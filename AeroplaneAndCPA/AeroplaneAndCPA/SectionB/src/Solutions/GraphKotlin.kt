package Solutions

import Task
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.*

class GraphKotlin(projectFile: String) : GraphInterface {
    private var start: GraphNode? = null
    private var finish: GraphNode? = null

    init {
        parseGraph(projectFile)
    }

    private fun setIncomingDegree() {
        setIncomingDegreeRecursively(start)
    }


    //YOU ARE ASKED TO IMPLEMENT THIS METHOD
    //description: It proceeds forward and recursively sets the degree of each
    //             node to be the number of its incoming edges. 
    //post: It sets the degree of each node to be equal to the number of incoming 
    //      edges of that node. 
    private fun setIncomingDegreeRecursively(node: GraphNode?) {
        var count = 0

        for (edge in node.getIncomingEdges()) {
            count++
        }

        node.setDegree(count)

        for (edge in node.getOutgoingEdges()) {
            setIncomingDegreeRecursively(edge.getEndPoint())
        }
    }

    //YOU ARE ASKED TO IMPLEMENT THIS METHOD
    //description: It returns a queue of GraphNodes whose FIFO ordering reflects the 
    //             order in which the earliest completion time for the nodes has to be computed.
    //		       See the description of the algorithm given in the questions.
    //post: It returns a queue of GraphNode objects such that a node added in the queue before 
    //      another node indicates that there is a path from the former node to the latter 
    //      node in the event-node graph.   
    private fun topologicalSort(): QueueInterface<GraphNode> {
        val tempQueue: QueueInterface<GraphNode> = java.util.Queue<Any>
        val resultQueue: QueueInterface<GraphNode> = java.util.Queue<Any>

        setIncomingDegree()

        tempQueue.enqueue(start)

        while (!tempQueue.isEmpty()) {
            val graphNode: GraphNode = tempQueue.dequeue()
            resultQueue.enqueue(graphNode)

            for (edge in graphNode.getOutgoingEdges()) {
                val current: GraphNode = edge.getEndPoint()
                current.setDegree(current.getDegree() - 1)
                if (current.getDegree() === 0) {
                    tempQueue.enqueue(current)
                }
            }
        }

        return resultQueue
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
        val sortedNodes: QueueInterface<GraphNode> = topologicalSort()
        val start: GraphNode = sortedNodes.dequeue()
        start.setEarliestCompletionTime(0)


        //YOU ARE ASKED TO IMPLEMENT THIS WHILE LOOP 
        while (!sortedNodes.isEmpty()) {
            val current: GraphNode = sortedNodes.dequeue()
            if (current.getIncomingEdges().size() === 1) {
                val currentEdge: GraphEdge = current.getIncomingEdges().get(1)
                current.setEarliestCompletionTime(
                    currentEdge.getTaskDuration()
                            + currentEdge.getStartPoint().EarliestCompletionTime()
                )
            } else {
                var max = 0
                for (currentEdge in current.getIncomingEdges()) {
                    max = Math.max(currentEdge.getStartPoint().EarliestCompletionTime(), max)
                }
                current.setEarliestCompletionTime(max)
            }
        }
    }

    val earliestCompletionTime: Int
        get() {
            computeEarliestCompletionTime()
            return finish.EarliestCompletionTime()
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

        val `in` = Scanner(FileReader(inputFile))
        // 1. read the task information
        val numOfTasks = `in`.nextLine().toInt()
        val tasks: MutableMap<String, GraphEdge> = Hashtable<String, GraphEdge>()

        for (i in 0 until numOfTasks) {
            val line =
                `in`.nextLine().trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            // line[0] is task name, line[1] is task duration
            val task: Task = Task(line[0], line[1].toInt())
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
                    val task: GraphEdge? = tasks[line[1]]
                    task.setEndPoint(finish)
                    finish.addIncomingEdge(task)
                } else { // line.length > 2,
                    // i.e., there are multiple dependences for the finish
                    // point, so need to
                    // create dummy node and dummy edge for each dependence
                    for (j in 1 until line.size) {
                        val task: GraphEdge? = tasks[line[j]]
                        val dummyNode: GraphNode = GraphNode()
                        task.setEndPoint(dummyNode)
                        dummyNode.addIncomingEdge(task)

                        val dummyTask: GraphEdge = GraphEdge(Task.DUMMY_TASK)
                        dummyTask.setStartPoint(dummyNode)
                        dummyNode.addOutgoingEdge(dummyTask)

                        dummyTask.setEndPoint(finish)
                        finish.addIncomingEdge(dummyTask)
                    }
                }
            } else {
                val task: GraphEdge? = tasks[line[0]]

                if (line.size == 2) {
                    // there is only one dependence
                    if (line[1] == "Start") {
                        // this task does not have any dependent task
                        task.setStartPoint(start)
                        start.addOutgoingEdge(task)
                    } else {
                        // this task has exactly one dependent task
                        val dependentTask: GraphEdge? = tasks[line[1]]
                        if (dependentTask.getEndPoint() != null) {
                            // join the two tasks
                            task.setStartPoint(dependentTask.getEndPoint())
                            dependentTask.getEndPoint().addOutgoingEdge(task)
                        } else {
                            val intermediateNode: GraphNode = GraphNode()
                            task.setStartPoint(intermediateNode)
                            intermediateNode.addOutgoingEdge(task)

                            dependentTask.setEndPoint(intermediateNode)
                            intermediateNode.addIncomingEdge(dependentTask)
                        }
                    }
                } else {
                    // there are multiple dependence
                    // i.e, need to create dummy node and dummy edge for each
                    // dependence
                    val dummyNode: GraphNode = GraphNode()
                    task.setStartPoint(dummyNode)
                    dummyNode.addOutgoingEdge(task)

                    for (j in 1 until line.size) {
                        val dependentTask: GraphEdge? = tasks[line[j]]

                        val dummyTask: GraphEdge = GraphEdge(Task.DUMMY_TASK)
                        dummyTask.setEndPoint(dummyNode)
                        dummyNode.addIncomingEdge(dummyTask)

                        if (dependentTask.getEndPoint() != null) {
                            dummyTask
                                .setStartPoint(dependentTask.getEndPoint())
                            dependentTask.getEndPoint().addOutgoingEdge(
                                dummyTask
                            )
                        } else {
                            val intermediateNode: GraphNode = GraphNode()
                            dependentTask.setEndPoint(intermediateNode)
                            intermediateNode.addIncomingEdge(dependentTask)

                            dummyTask.setStartPoint(intermediateNode)
                            intermediateNode.addOutgoingEdge(dummyTask)
                        }
                    }
                }
            }
        }
    }
}



