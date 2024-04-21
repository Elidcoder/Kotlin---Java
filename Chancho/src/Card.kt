package src

/**
 * The Card class models a standard playing card. Cards are constant; their
 * values cannot be changed after they are created.
 *
 * Access methods to the Card's rank value and suit value are provided.
 */
class Card(val rank: Rank?, val suit: Suit?) {
    override fun equals(obj: Any?): Boolean {
        if (obj !is Card) {
            return false
        }
        val card = obj
        return rank == card.rank && suit == card.suit
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = hash * 31 + (rank?.hashCode() ?: 0)
        hash = hash * 31 + (suit?.hashCode() ?: 0)
        return hash
    }

    // Return a String representation of this card, i.e. a description of the
    // Card's rank and suit.
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(rank)
        sb.append(" of ")
        sb.append(suit)
        return sb.toString()
    }
}