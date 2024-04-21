package src

import java.util.*

val NUMB_AUTOMATIC = 3

/**
 * This class provides the entry-point to the Chancho game. The class is
 * responsible for:
 *
 * -- creating players, organising them in a circle and allocating card-piles
 * between each player.
 *
 * -- creating a Chancho game-deck of the appropriate size.
 *
 * -- shuffling the game-deck.
 *
 * -- creating a game dealer.
 *
 * -- instructing the game dealer to begin the game.
 *
 */
object Chancho {
    @JvmStatic
    fun main(args: Array<String>) {
        // Set up players in a circle with their buffers

        val players = setupPlayers()

        // Create appropriate sized game-deck for number of players
        var gameDeck: Deck? = createGameDeck(players.size * 4)

        // Shuffle deck a random number of times
        // (but avoiding 2 mod 4, which makes everyone win right away)
        val timesToShuffle = 7 + Random().nextInt(3)
        for (i in 0 until timesToShuffle) {
            gameDeck = gameDeck!!.riffleShuffle(gameDeck.cut())
        }

        // Start game
        Dealer(players, gameDeck as Deck).playGame()
    }

    private fun setupPlayers(): Set<Player> {
        val players = LinkedHashSet<Player>()
        val cardPile1 = CardPile(1)
        val cardPile2 = CardPile(1)
        val cardPile3 = CardPile(1)
        val cardPile4 = CardPile(1)

        players.add(AutoPlayer(cardPile1, cardPile2, "P1"))
        players.add(AutoPlayer(cardPile2, cardPile3, "P2"))
        players.add(AutoPlayer(cardPile3, cardPile4, "P3"))
        players.add(ManualPlayer(cardPile4, cardPile1, "P4"))
        return players
    }

    /**
     * Create a game-deck with 'size' cards, by adding complete sets of
     * suit-values for each rank-value in ascending order of rank.
     */
    private fun createGameDeck(size: Int): Deck {
        var size = size
        assert(size % 4 == 0) { "Attempting to create a game-deck whose size is not a multiple of 4." }

        val deck: Deck = MinHeapDeck()

        addCards@ for (rank in Rank.values()) {
            for (suit in Suit.values()) {
                if (size-- > 0) {
                    deck.addToBottom(Card(rank, suit))
                } else {
                    break@addCards
                }
            }
        }

        return deck
    }
}
