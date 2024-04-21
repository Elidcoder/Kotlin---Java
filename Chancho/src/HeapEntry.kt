package src

/**
 * The HeapEntry class provides three basic functions:
 *
 * 1. It wraps an object.
 *
 * 2. It associates an integer called 'position' with the wrapped object.
 *
 * 3. It defines a natural ordering between HeapEntry<T> objects based
 * solely on the value of the 'position' field. The defined ordering
 * follows the natural ordering on integers.
 *
 * Note: this class has a natural ordering that is inconsistent with equals(..),
 * i.e. For some instances a, b of class HeapEntry,
 * (a.equals(b)) is not the same as (a.compareTo(b)==0)
</T> */
class HeapEntry<T>(item: T, position: Int) : Comparable<HeapEntry<T>> {
    private var item: T? // the wrapped object

    // return the 'position' associated with the object
    var position: Int // the wrapped object's positions

    init {
        this.item = item
        this.position = position
    }

    // return the wrapped object
    fun getItem(): T? {
        return item
    }

    // returns a negative integer, zero, or a positive integer as this
    // HeapEntry<T> is less than, equal to, or greater than the specified
    // HeapEntry<T>.
    override fun compareTo(item: HeapEntry<T>): Int {
        return position - item.position
    }

    fun setItem(item: T) {
        this.item = item
    }

    override fun equals(obj: Any?): Boolean {
        if (obj !is HeapEntry<*>) {
            return false
        }

        val heapEntry = obj
        return (position == heapEntry.position) && (item == heapEntry.item)
    }



    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Position ")
        sb.append(position)
        sb.append(": Item ")
        sb.append(item)
        return sb.toString()
    }

    override fun hashCode(): Int {
        var result = item?.hashCode() ?: 0
        result = 31 * result + position
        return result
    }
}