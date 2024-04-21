package src

/**
 * This class implements a Deck of playing cards using a min-heap to store the
 * cards. Complete implementations of the methods: size(), isEmpty(),
 * addToBottom(Card) and removeFromTop() have been provided and should not be
 * modified.
 *
 * The developer should implement the two Deck shuffling operations: cut() and
 * riffleShuffle(Deck).
 */
class MinHeapDeck : Deck {
    private val heap: IMinHeap<HeapEntry<Card>> = MinHeap()

    override fun size(): Int {
        return heap.size()
    }

    override val isEmpty: Boolean
        get() = heap.isEmpty

    override fun addToBottom(card: Card) {
        checkNotNull(card) { "Attempting to add 'null' to a MinHeapDeck" }

        heap.add(HeapEntry(card, size()))
    }

    override fun removeFromTop(): Card {
        assert(!isEmpty) { "Attempting to remove a card from an empty MinHeapDeck." }
        return heap.removeMin().getItem() as Card
    }

    override fun cut(): Deck {
        val topHalf = MinHeapDeck()
        for (card in 0 until heap.size()/2){
            topHalf.addToBottom(removeFromTop())
        }
        return topHalf
    }

    override fun riffleShuffle(otherDeck: Deck): Deck {
		val newDeck = MinHeapDeck()
        while (!isEmpty){
            newDeck.addToBottom(removeFromTop())
            newDeck.addToBottom(otherDeck.removeFromTop())
        }
        return newDeck
    }

    override fun toString(): String {
        return heap.toString()
    }
}
