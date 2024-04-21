package src

/**
 * This class implements a min-heap abstract data type (as described by the
 * generic interface IMinHeap<T extends Comparable></T><T>>) using a fixed array of
 * size MinHeap.MAXIMUM_HEAP_SIZE.
</T> */
class MinHeap<T : Comparable<T>>() : IMinHeap<T> {
    private var elements: MutableList<T> = emptyList<T>().toMutableList()

    override fun add(element: T) {
        elements.add(element)
        elements.sort()
    }

    override fun removeMin(): T {
        if (elements.isEmpty()) throw NoSuchElementException()
        return elements.removeAt(0)
    }

    override val min: T
        get() = if (isEmpty) {throw NoSuchElementException()} else elements[0]

    override val isEmpty: Boolean
        get() = elements.isEmpty()

    override fun size(): Int = elements.size
}