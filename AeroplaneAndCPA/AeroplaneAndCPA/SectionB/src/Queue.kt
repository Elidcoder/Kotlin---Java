class Queue<T> : QueueInterface<T?> {
    private var first: Node<T?>? = null
    private var last: Node<T?>? = null

    override val isEmpty: Boolean
        get() = last == null

    //post: Adds the given item to the queue
    override fun enqueue(item: T?) {
        val newNode = Node(item)
        if (isEmpty){
            first = newNode
        }
        else{
            last!!.next = newNode
        }
        last = newNode
    }

    //post: Removes and returns the head of the queue. It throws an 
    //      exception if the queue is empty.
    @Throws(QueueException::class)
    override fun dequeue(): T {
        if (isEmpty){
            throw NoSuchElementException()
        }
        else{
            assert (first != null)
            val topNode = first
            first = first!!.next
            if (first == null){
                last = null
            }
            return topNode!!.item as T
        }
    }
}
