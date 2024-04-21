interface GenericListInterface<T> : Iterable<T> {
    fun size(): Int


    val isEmpty: Boolean


    @Throws(ListIndexOutOfBoundsException::class)
    fun get(index: Int): T


    @Throws(ListIndexOutOfBoundsException::class)
    fun add(index: Int, newItem: T)


    @Throws(ListIndexOutOfBoundsException::class)
    fun remove(index: Int)
}
