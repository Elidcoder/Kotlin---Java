package src

/**
 * Interface for a deck of playing cards. Single cards may only be added to the
 * bottom of the deck and removed from the top. Two card shuffling operations
 * are defined on any deck: the cut and the riffle-shuffle. Some standard
 * operations to query the size of the Deck are also made available.
 */
interface Deck {
    // add the provided card to the bottom of this deck.
    fun addToBottom(card: Card)

    // return the top half of this deck, leaving the bottom half as
    // this deck.
    fun cut(): Deck

    // returns true if this deck contains no cards.
    val isEmpty: Boolean

    // remove and return the card from the top of this deck.
    fun removeFromTop(): Card

    // return a new deck representing the interleaving of this deck
    // with the parameter deck. The top card of the new deck should
    // be the top card of this deck.
    fun riffleShuffle(deck: Deck): Deck

    // return the number of cards in this deck.
    fun size(): Int
}