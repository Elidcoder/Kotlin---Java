interface QueueInterface<T> {
    fun enqueue(newEntry: T)

    fun dequeue(): T

    val isEmpty: Boolean
}
