class GenericList<T> // end default constructor;
    : GenericListInterface<T?> {
    private var head: Node<T?>? = null //reference to the first element in the list
    private var numItems = 0 // number of items in the list

    override val isEmpty: Boolean
        get() = (numItems == 0) // end isEmpty

    override fun size(): Int {
        return numItems
    } // end size

    private fun find(pos: Int): Node<T?>? {
        var curr = head
        for (skip in 1 until pos) {
            curr = curr!!.next
        }
        return curr
    } //end find

    @Throws(ListIndexOutOfBoundsException::class)
    override fun get(pos: Int): T? {
        if (pos >= 1 && pos <= numItems) {
            val curr = find(pos)
            val dataItem = curr!!.item
            return dataItem
        } else {
            throw ListIndexOutOfBoundsException("position out of range in get")
        }
    } // end get

    @Throws(ListIndexOutOfBoundsException::class)
    override fun add(pos: Int, item: T?) {
        if (pos >= 1 && pos <= numItems + 1) {
            if (pos == 1) {
                val newNode = Node(item)
                newNode.next = head
                head = newNode
            } else {
                val newNode = Node(item)
                val prev = find(pos - 1)
                newNode.next = prev!!.next
                prev.next = newNode
            }
            numItems++
        } else {
            throw ListIndexOutOfBoundsException("position out of range in add")
        }
    } // end add

    @Throws(ListIndexOutOfBoundsException::class)
    override fun remove(pos: Int) {
        if (pos >= 1 && pos <= numItems) {
            if (pos == 1) {
                head = head!!.next
            } else {
                val prev = find(pos - 1)
                val curr = prev!!.next
                prev.next =curr!!.next
            }
            numItems--
        } else {
            throw ListIndexOutOfBoundsException("position out of range in remove")
        }
    } // end remove

    //post:  Returns a list iterator object. 
    override fun iterator(): MutableIterator<T> = GenericListIterator(head) as MutableIterator<T>


    private inner class GenericListIterator(private var current: Node<T?>?) : MutableIterator<T?> {
        private var lastReturned: Node<T?>? = null

        /*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
        override fun hasNext(): Boolean {
            return current != null
        }

        /*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
        override fun next(): T? {
            val result = current!!.item
            lastReturned = current
            current = current!!.next
            return result
        }

        override fun remove() {
            throw UnsupportedOperationException()
        }
    }
} //end GenericList<T>




