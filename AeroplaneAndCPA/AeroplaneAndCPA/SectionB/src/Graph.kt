import java.io.FileNotFoundException
import java.io.FileReader
import java.io.InvalidObjectException
import java.util.*
import kotlin.math.max

class Graph(projectFile: String) : GraphInterface {
    private var start: GraphNode? = null
    private var finish: GraphNode? = null

    init {
        parseGraph(projectFile)
    }

    private fun setIncomingDegree() {
        start?.run{
            setIncomingDegreeRecursively(start!!)
        }

    }


    //YOU ARE ASKED TO IMPLEMENT THIS METHOD
    //description: It proceeds forward and recursively sets the degree of each
    //             node to be the number of its incoming edges. 
    //post: It sets the degree of each node to be equal to the number of incoming 
    //      edges of that node. 
    private fun setIncomingDegreeRecursively(node: GraphNode) {
        node.degree = node.incomingEdges.size()

        for (edge in node.outgoingEdges) {
            setIncomingDegreeRecursively(edge!!.endPoint!!)
        }
    }

    //YOU ARE ASKED TO IMPLEMENT THIS METHOD
    //description: It returns a queue of GraphNodes whose FIFO ordering reflects the 
    //             order in which the earliest completion time for the nodes has to be computed.
    //		       See the description of the algorithm given in the questions.
    //post: It returns a queue of GraphNode objects such that a node added in the queue before 
    //      another node indicates that there is a path from the former node to the latter 
    //      node in the event-node graph.   
    private fun topologicalSort(): Queue<GraphNode> {
        start?.run{
            var curNode: GraphNode
            setIncomingDegree()
            var temporaryQueue = Queue<GraphNode>()
            var resultQueue = Queue<GraphNode>()
            temporaryQueue.enqueue(start!!)
            while (!temporaryQueue.isEmpty){
                curNode = temporaryQueue.dequeue()
                resultQueue.enqueue(curNode)
                for (edge in curNode.outgoingEdges){
                    val currentNode = edge!!.endPoint
                    currentNode!!.degree --
                    if (currentNode.degree == 0){
                        temporaryQueue.enqueue(currentNode)
                    }

                }
            }
            return resultQueue
        }
        throw InvalidObjectException("Invalid start given")
    }


    //YOU ARE ASKED TO COMPLETE THE IMPLEMENTATION OF THIS METHOD
    //description: It computes the earliest completion time for each node using the algorithm 
    //             described in the question, in the order given by the FIFO queue generated 
    //             by the topological sort.
    //post: Sets the earliest completion time for the start node to be zero. Sets the
    //      earliest completion time for each node, different from the start node, to be the 
    //      maximum of the sum of the earliest completion time for its precedent node and the 
    //      duration of the connecting edge, over all its precedent nodes. 
    private fun computeEarliestCompletionTime() {
        val sortedNodes = topologicalSort()
        val start = sortedNodes.dequeue()
        start!!.setEarliestCompletionTime(0)


        //YOU ARE ASKED TO IMPLEMENT THIS WHILE LOOP
        while (!sortedNodes.isEmpty) {

        }
        val current = sortedNodes.dequeue()
            if (current.incomingEdges.size() == 1) {
                val currentEdge = current.incomingEdges.get(1)
                current!!.setEarliestCompletionTime(
                    currentEdge!!.taskDuration
                            + currentEdge.startPoint!!.EarliestCompletionTime()
                )
            } else {
                var max = 0
                for (currentEdge in current.incomingEdges) {
                    max = max(currentEdge!!.startPoint!!.EarliestCompletionTime().toDouble() + currentEdge.taskDuration, max.toDouble())
                        .toInt()
                }
                current!!.setEarliestCompletionTime(max)
            }
    }

    override val earliestCompletionTime: Int
        get() {
            computeEarliestCompletionTime()
            return finish!!.EarliestCompletionTime()
        }


    @Throws(FileNotFoundException::class)
    private fun parseGraph(inputFile: String) {
        // each project input file has the following format:
        // 1. first line is an integer N, followed by N lines:
        // a. each of these N lines has the format "task_name task_duration"
        // 2. (N+1)th line is an integer M, followed by M lines:
        // a. each of these M lines has the format
        // "task_name dependent_task_name1 dependent_task_name2 ..."
        // b. "Finish" denotes the end of the project; "Start" denotes the start
        // of the project

        val `in`: Scanner = Scanner(FileReader(inputFile))
        // 1. read the task information
        val numOfTasks = `in`.nextLine().toInt()
        val tasks: MutableMap<String, GraphEdge> = Hashtable<String, GraphEdge>()

        for (i in 0 until numOfTasks) {
            val line =
                `in`.nextLine().trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            // line[0] is task name, line[1] is task duration
            val task = Task(line[0], line[1].toInt())
            tasks[line[0]] = GraphEdge(task)
        }
        // 2. read the task dependence information and construct the dependence
        // graph
        val numOfDependencies = `in`.nextLine().toInt()
        //Graph graph = new Graph();
        start = GraphNode()

        for (i in 0 until numOfDependencies) {
            val line =
                `in`.nextLine().trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            // line[0] is the task, line[1]... are the pre-tasks of line[0]
            if (line[0] == "Finish") {
                finish = GraphNode()
                if (line.size == 2) {
                    // there is only one dependence of the finish point
                    val task = tasks[line[1]]
                    task!!.endPoint = finish
                    finish!!.addIncomingEdge(task)
                } else { // line.length > 2,
                    // i.e., there are multiple dependences for the finish
                    // point, so need to
                    // create dummy node and dummy edge for each dependence
                    for (j in 1 until line.size) {
                        val task = tasks[line[j]]
                        val dummyNode = GraphNode()
                        task!!.endPoint = dummyNode
                        dummyNode.addIncomingEdge(task)

                        val dummyTask = GraphEdge(Task.Companion.DUMMY_TASK)
                        dummyTask.startPoint = dummyNode
                        dummyNode.addOutgoingEdge(dummyTask)

                        dummyTask.endPoint = finish
                        finish!!.addIncomingEdge(dummyTask)
                    }
                }
            } else {
                val task = tasks[line[0]]

                if (line.size == 2) {
                    // there is only one dependence
                    if (line[1] == "Start") {
                        // this task does not have any dependent task
                        task!!.startPoint = start
                        start!!.addOutgoingEdge(task)
                    } else {
                        // this task has exactly one dependent task
                        val dependentTask = tasks[line[1]]
                        if (dependentTask?.endPoint != null) {
                            // join the two tasks
                            task!!.startPoint = (dependentTask.endPoint)
                            dependentTask.endPoint!!.addOutgoingEdge(task)
                        } else {
                            val intermediateNode = GraphNode()
                            task!!.startPoint = (intermediateNode)
                            intermediateNode.addOutgoingEdge(task)

                            dependentTask!!.endPoint = intermediateNode
                            intermediateNode.addIncomingEdge(dependentTask)
                        }
                    }
                } else {
                    // there are multiple dependence
                    // i.e, need to create dummy node and dummy edge for each
                    // dependence
                    val dummyNode = GraphNode()
                    task!!.startPoint = dummyNode
                    dummyNode.addOutgoingEdge(task)

                    for (j in 1 until line.size) {
                        val dependentTask = tasks[line[j]]

                        val dummyTask = GraphEdge(Task.Companion.DUMMY_TASK)
                        dummyTask.endPoint = dummyNode
                        dummyNode.addIncomingEdge(dummyTask)

                        if (dependentTask?.endPoint != null) {
                            dummyTask.startPoint = dependentTask.endPoint
                            dependentTask.endPoint!!.addOutgoingEdge(
                                dummyTask
                            )
                        } else {
                            val intermediateNode = GraphNode()
                            dependentTask!!.endPoint = intermediateNode
                            intermediateNode.addIncomingEdge(dependentTask)

                            dummyTask.startPoint = intermediateNode
                            intermediateNode.addOutgoingEdge(dummyTask)
                        }
                    }
                }
            }
        }
    }
}



