package aeroplane

class Seat(private val row: Int, private val letter: Char) {
    private val MAXROW = 50
    private val MAXLETTER = 'F'
    private val EMERGENCYEXITS = listOf(1,10,30)

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

    companion object {
        const val SMALLEST_LETTER: Char = 'A'
        const val CREW_ROW: Int = 1
        const val BUSINESS_FIRST_ROW: Int = 2
        const val BUSINESS_LAST_ROW: Int = 15
        const val ECONOMY_FIRST_ROW: Int = 16
        const val ECONOMY_LAST_ROW: Int = 50
    }

    override fun toString(): String {
        return "$letter$row"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seat

        if (row != other.row) return false
        if (letter != other.letter) return false
        if (MAXROW != other.MAXROW) return false
        if (MAXLETTER != other.MAXLETTER) return false
        if (EMERGENCYEXITS != other.EMERGENCYEXITS) return false

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
