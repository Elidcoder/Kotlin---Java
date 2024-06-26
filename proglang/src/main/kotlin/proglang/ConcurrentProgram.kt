package proglang

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class ConcurrentProgram(val stmts: List<Stmt>, val times: List<Long>) {
    init {
        require(times.size == stmts.size) {throw IllegalArgumentException()}
    }
    val lock:ReentrantLock = ReentrantLock()

    fun execute(initialStore: Map<String, Int>): Map<String, Int> {
        val workingStore = HashMap<String, Int>(initialStore)
        val threads: List<Thread>  = stmts.indices.map {Thread(ProgramExecutor(stmts[it], lock, times[it], workingStore))}
        threads.forEach {it.start()}
        threads.forEach { it.join() }
        return workingStore
    }

}