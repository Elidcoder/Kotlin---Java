package src

import java.util.*

/**
 * The ManualPlayer class models a Chancho card game player sitting at a
 * computer terminal. The card selection strategy is determined by the manual
 * player, who simply inputs their choice into the computer via the keyboard.
 */
class ManualPlayer(discardPile: CardPile, pickupPile: CardPile, name: String) : AbstractPlayer(discardPile, pickupPile,
    name
) {
    private val scanner = Scanner(System.`in`)

    override fun selectCard(): Int {
        println("These are your cards:")
        println(super.toString())
        println(
            "Please input card number to discard: (0 to "
                    + (Player.Companion.HANDSIZE - 1) + ")"
        )
        var cardIndex = scanner.nextInt()
        while (cardIndex < 0 || cardIndex >= Player.Companion.HANDSIZE) {
            println(
                "Invalid card index. Please enter number between 0 and "
                        + (Player.Companion.HANDSIZE - 1)
            )
            cardIndex = scanner.nextInt()
        }
        return cardIndex
    }
}