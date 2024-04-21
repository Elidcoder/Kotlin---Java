package src


/**
 * Interface for a min-heap abstract data type. The ADT is sorted according to
 * the natural ordering of its elements (see java.lang.Comparable). Some
 * methods are provided which allow the user access to the least item within
 * the collection, and obtain information about the size of the heap.
 *
 * All elements inserted into a min-heap must implement the Comparable
 * interface. All min-heap implementation classes must provide a void
 * constructor (with no arguments) that creates an empty min-heap, sorted
 * according to the natural ordering of its elements.
 *
 */
interface IMinHeap<T : Comparable<T>> {
    // add an element to the appropriate position of this min-heap.
    // throw a HeapException if there is no more space on the heap.
    @Throws(HeapException::class)
    fun add(element: T)

    // remove and return the least element from this min-heap.
    fun removeMin(): T

    // return the least element in this min-heap.
    val min: T

    // return true if this min-heap is empty.
    val isEmpty: Boolean

    // return the number of elements in this min-heap.
    fun size(): Int
}