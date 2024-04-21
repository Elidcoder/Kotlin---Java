package src

/**
 * This class provides a skeletal implementation of the Player interface. A
 * complete implementation is given for the methods addToHand(Card), pickup()
 * and hasWon(). The discard() method calls an abstract method selectCard()
 * which implements the player's card selection strategy.
 *
 * The developer need only subclass this abstract class and define the
 * selectCard() method.
 */
abstract class AbstractPlayer internal constructor(
    private val discardPile: CardPile,
    private val pickupPile: CardPile,
    private val name: String
) : Player {
    protected val cards: Array<Card?> = arrayOfNulls<Card>(Player.Companion.HANDSIZE)

    // A card selection strategy that will determine which card will be
    // discarded by this player. The method returns an index into the
    // 'cards' array.
    protected abstract fun selectCard(): Int

    override fun addToHand(card: Card?): Boolean {
        checkNotNull(card) { "Cannot add 'null' to the player's hand." }

        for (i in cards.indices) {
            if (cards[i] == null) {
                cards[i] = card
                return true
            }
        }
        return false
    }

    override fun discard() {
        val selected = selectCard()
        discardPile.put(cards[selected])
        cards[selected] = null
    }

    override fun pickup() {
        assert(!pickupPile.isEmpty) { "No cards to pick up from card pile." }
        addToHand(pickupPile.get())
    }

    override fun hasWon(): Boolean {
        checkNotNull(cards[0]) { "Card 0 of player's hand contains a 'null' reference." }

        val rank = cards[0]!!.rank
        for (i in 1 until cards.size) {
            checkNotNull(cards[i]) { "Card $i of player's hand contains a 'null' reference." }

            if (cards[i]!!.rank != rank) {
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Player ")
        sb.append(name)
        sb.append(". Cards in hand:\n")
        var i = 0
        for (card in cards) {
            sb.append(i++)
            sb.append(") ")
            sb.append(card)
            sb.append("\n")
        }
        return sb.toString()
    }
}