package src

/**
 * The Player interface models a Chancho game player.
 *
 * Developers must ensure the player is not in possession of more than HANDSIZE
 * cards on any occasion.
 */
interface Player {
    // select a card to discard from the player's hand and
    // place it on the CardPile to the player's left.
    fun discard()

    // pick up a card from the CardPile on the player's right and
    // add it to the player's hand.
    fun pickup()

    // returns true if this Player has won the game. i.e. if the player holds
    // all the suit values for a particular card rank.
    fun hasWon(): Boolean

    // add the provided card to this player's hand.
    fun addToHand(card: Card?): Boolean

    companion object {
        // the maximum number of cards a Player may hold.
        const val HANDSIZE: Int = 4
    }


}