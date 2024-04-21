class GraphEdge(private val task: Task) {
    var startPoint: GraphNode? = null
    var endPoint: GraphNode? = null

    val taskName: String?
        get() = task.name

    val taskDuration: Int
        get() = task.duration
}
