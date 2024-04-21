package Solutions

class Queue<T> : QueueInterface<T> {
    private var first: Node<T>? = null
    private var last: Node<T>? = null

    val isEmpty: Boolean
        get() = last == null

    //post: Adds the given item to the queue
    override fun enqueue(item: T) {
        val newNode: Node<T> = Node<T>(item)

        if (isEmpty) {
            first = newNode
        } else {
            last?.setNext(newNode)
        }

        last = newNode
    }

    //post: Removes and returns the head of the queue. It throws an 
    //      exception if the queue is empty.
    @Throws(QueueException::class)
    override fun dequeue(): T {
        if (isEmpty) {
            throw QueueException("Error! Queue is empty!")
        } else {
            val oldHead: T = first!!.getItem()
            if (first === last) {
                first = null
                last = null
            } else {
                first = first!!.getNext()
            }
            return oldHead
        }
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}
