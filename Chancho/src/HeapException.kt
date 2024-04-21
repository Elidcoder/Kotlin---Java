package src

/**
 * Thrown when an attempt is made to add an element to a MinHeap and
 * there is no more space in the underlying array representation.
 */
class HeapException(message: String?) : RuntimeException(message)