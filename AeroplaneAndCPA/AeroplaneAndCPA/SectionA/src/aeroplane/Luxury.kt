package aeroplane

import java.util.*

enum class Luxury {
    CHAMPAGNE, TRUFFLES, STRAWBERRIES;

    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}
