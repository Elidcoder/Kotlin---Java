package src

import java.util.*

/**
 * The CardPile class models a pile of playing cards. The maximum number of cards
 * allowed in the pile is determined when the class is initialised. Operations
 * are provided that allow a Card to be added and removed from the card pile.
 */
class CardPile internal constructor(// the maximum number of cards allowed in
    private val maximumSize: Int
) {
    private val pile = Stack<Card>() // the cards currently in the pile

    val isEmpty: Boolean
        get() = pile.isEmpty()

    // add a card to the top of the pile if there is enough space.
    // returns true if the card was successfully added to the pile, otherwise
    // returns false.
    fun put(card: Card?): Boolean {
        if (pile.size < maximumSize) {
            pile.push(card)
            return true
        }
        return false
    }

    // remove and return the card at the top of the pile.
    fun get(): Card {
        return pile.pop()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("CardPile{ maxSize=")
        sb.append(maximumSize)
        sb.append(", cards=")
        sb.append(pile.toString())
        sb.append("}")
        return sb.toString()
    }
}