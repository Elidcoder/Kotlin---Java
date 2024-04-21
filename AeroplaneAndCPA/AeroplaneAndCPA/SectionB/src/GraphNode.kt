class GraphNode {
    val label: String
    val incomingEdges: GenericListInterface<GraphEdge?>
    val outgoingEdges: GenericListInterface<GraphEdge?>
    private var ec = 0
    var degree: Int = 0

    init {
        label = count++.toString() // generate unique id for each node instance
        incomingEdges = GenericList()
        outgoingEdges = GenericList()
    }

    fun addIncomingEdge(`in`: GraphEdge?) {
        incomingEdges.add(1, `in`)
    }

    fun addOutgoingEdge(out: GraphEdge?) {
        outgoingEdges.add(1, out!!)
    }

    fun setEarliestCompletionTime(ec: Int) {
        this.ec = ec
    }

    fun EarliestCompletionTime(): Int {
        return ec
    }


    companion object {
        private var count = 0
    }
}
