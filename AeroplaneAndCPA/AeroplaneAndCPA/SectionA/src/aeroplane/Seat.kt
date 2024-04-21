package aeroplane

 val MAXROW = 50
 val MAXLETTER = 'F'
 val EMERGENCYEXITS = listOf(1,10,30)
 val CREW_FIRST_ROW: Int = 1
 val CREW_LAST_ROW: Int = 1
 val BUSINESS_FIRST_ROW: Int = 2
 val BUSINESS_LAST_ROW: Int = 15
 val ECONOMY_FIRST_ROW: Int = 16
 val ECONOMY_LAST_ROW: Int = 50
class Seat(private val row: Int, private val letter: Char) {


    init {
        require (row in 1..MAXROW)
        require (letter in 'A'..MAXLETTER)
    }

    fun isEmergencyExit() = row in EMERGENCYEXITS

    fun hasNext(): Boolean = row != MAXROW || letter != MAXLETTER

    fun next(): Seat {
        require(hasNext()) { NoSuchElementException() }
        if (letter == MAXLETTER) {
            return Seat(row + 1, 'A')
        }
        return Seat(row, letter + 1)
    }
    fun isCrew():Boolean = row in CREW_FIRST_ROW..CREW_LAST_ROW

    fun isBusiness():Boolean = row in BUSINESS_FIRST_ROW..BUSINESS_LAST_ROW

    fun isEconomy():Boolean = row in ECONOMY_FIRST_ROW..ECONOMY_LAST_ROW

    override fun toString(): String {
        return "$letter$row"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seat

        if (row != other.row) return false
        if (letter != other.letter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + letter.hashCode()
        result = 31 * result + MAXROW
        result = 31 * result + MAXLETTER.hashCode()
        result = 31 * result + EMERGENCYEXITS.hashCode()
        return result
    }
}
