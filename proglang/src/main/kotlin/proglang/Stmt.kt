package proglang

sealed interface Stmt {
    var next: Stmt?
    var lastInSequence: Stmt
        get() = next?.lastInSequence ?: this
        set(value) {}
    abstract fun toString(indent: Int): String
    abstract fun clone(): Stmt
    class Assign(val name: String, val expr: IntExpr, override var next: Stmt? = null): Stmt {

        override fun toString(indent: Int): String {
            return " ".repeat(indent) + name + " = $expr\n" + (next?.toString(indent) ?: "")
        }
        override fun toString(): String = toString(0)
        override fun clone(): Stmt = Assign(name, expr, next?.clone())
    }

    class If(val condition: BoolExpr, val thenStmt: Stmt, val elseStmt: Stmt? = null, override var next: Stmt? = null): Stmt {
        val EXTRA_SPACES = 4
        override fun toString(indent: Int): String {
            val extraSpaces = " ".repeat(indent)
            return extraSpaces + "if ($condition) {\n${thenStmt.toString(indent + EXTRA_SPACES)}$extraSpaces}" + (elseStmt?.run{" else {\n${elseStmt.toString(indent + EXTRA_SPACES)}$extraSpaces}"} ?: "") + "\n" + (next?.toString(indent) ?: "")
        }
        override fun toString(): String = toString(0)
        override fun clone(): Stmt = If(condition, thenStmt.clone(), elseStmt?.clone(), next?.clone())
    }
    class While(val condition: BoolExpr, val body: Stmt?, override var next: Stmt? = null): Stmt {
        val EXTRA_SPACES = 4
        override fun toString(indent: Int): String {
            val extraSpaces = " ".repeat(indent)
            return extraSpaces + "while ($condition) {" + (body?.run{"\n${body.toString(indent + EXTRA_SPACES)}$extraSpaces}"} ?: "") + "\n" + (next?.toString(indent) ?: "")
        }
        override fun toString(): String = toString(0)
        override fun clone(): Stmt = While(condition, body?.clone(), next?.clone())
    }
}

fun Stmt.step(store: MutableMap<String, Int>): Stmt?{
    when (this) {
        is Stmt.Assign -> {
            val rhsValue = expr.eval(store)
            if (name in store.keys){
                store[name] = rhsValue
            }else{
                store[name] = rhsValue
            }
            return next
        }
        is Stmt.If -> {
            return if (condition.eval(store)){
                val followingStmt = thenStmt
                followingStmt.lastInSequence.next = next
                followingStmt
            } else{
                println(elseStmt)
                if (elseStmt != null){
                    val followingStmt = elseStmt
                    followingStmt.lastInSequence.next = next
                    followingStmt
                }
                else{
                    next
                }
            }
        }
        is Stmt.While -> {
            return if (condition.eval(store)){
                if (body != null){
                    val followingStmt = body.clone()
                    followingStmt.lastInSequence.next = this
                    followingStmt
                } else{
                    this
                }

            } else{
                next
            }
        }
    }
}
