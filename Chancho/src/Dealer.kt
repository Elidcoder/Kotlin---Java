package src

/**
 * The class Dealer encapsulates the actions of a Chancho game-dealer. The game
 * dealer is responsible for dealing cards from the provided game-deck to each
 * player, and scheduling rounds of the game until a player has won the game.
 * All players who have declared themselves as a winner should be congratulated.
 *
 * Developers should provide the constructor,
 *
 * public Dealer(Set<Player> players, Deck gameDeck);
 *
</Player> */
class Dealer (private val set: Set<Player>,private val game: Deck){
    init{
        for (x in 0 until HAND_SIZE){
            set.forEach{it.addToHand(game.removeFromTop())}
        }
    }

    fun playGame(){
        while (!set.any(Player::hasWon)){
            set.forEach(Player::discard)
            set.forEach(Player::pickup)
        }
        congratulateWinners()
    }

    private fun congratulateWinners(){
        println("The game has been won! Congratulations to:")
        set.filter(Player::hasWon).forEach(::println)
    }
}
