object Scheduler {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val graph: GraphInterface = Graph(args[0])
        println(
            "Earliest completion time for this project is "
                    + graph.earliestCompletionTime
        )
    }
}
