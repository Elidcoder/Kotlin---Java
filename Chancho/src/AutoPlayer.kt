package src

import java.util.Random

val HAND_SIZE = 4

class AutoPlayer(discardPile: CardPile, pickupPile: CardPile, name: String) : AbstractPlayer(discardPile, pickupPile,
    name
) {
    override fun selectCard(): Int {
        val removalOptions = mutableListOf<Int>()
        for (cardIndex in 0 until HAND_SIZE){
            if (cards.count{it!!.rank == cards[cardIndex]!!.rank} <= 1){
                removalOptions.add(cardIndex)
            }
        }
        if (removalOptions.isEmpty()){
            removalOptions.addAll(0 until HAND_SIZE)
        }
        val removedItemIndex = removalOptions[Random().nextInt(removalOptions.size)]
        return removedItemIndex
    }
}
