interface GraphInterface {
    @get:Throws(GraphException::class)
    val earliestCompletionTime: Int
}

