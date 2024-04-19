package aeroplane

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class SeatAllocator {
    private val allocation: MutableMap<Seat?, Passenger?> = HashMap()

    override fun toString(): String {
        return allocation.toString()
    }

    @Throws(AeroplaneFullException::class)
    private fun allocateInRange(
        passenger: Passenger?,
        first: Seat?, last: Seat
    ) {
        var currentSeat = first ?: //something else
        val allocated = false
        while (!allocated) {
            if (canUse(passenger, currentSeat)) {

            }
            else{
                if (currentSeat == last || !currentSeat.hasNext()) {}
                currentSeat = currentSeat.next()
            }
        }
    }
    /*
     @Throws(AeroplaneFullException::class)
    private fun allocateInRange(
        passenger: Passenger?,
        first: Seat?, last: Seat
    ) {
        var added = false
        var isLast = first == last
        var current = first

        while ((current!!.hasNext() || current == last) && !added && !isLast) {
            if ((!allocation.containsKey(current))) {
                if (!current.isEmergencyExit || passenger!!.isAdult) {
                    allocation[current] = passenger
                    added = true
                }
            }
            if (current == last) {
                isLast = true
            } else {
                current = current.next()
            }
        }

        if (!added) {
            throw AeroplaneFullException()
        }
    }
    */

    @Throws(IOException::class, AeroplaneFullException::class)
    fun allocate(filename: String?) {

    }
    /*
    @Throws(IOException::class, AeroplaneFullException::class)
    fun allocate(filename: String?) {
        val br = BufferedReader(FileReader(filename))

        var line: String
        while ((br.readLine().also { line = it }) != null) {
            try {
                if (line == CREW) {
                    allocateCrew(br)
                } else if (line == BUSINESS) {
                    allocateBusiness(br)
                } else if (line == ECONOMY) {
                    allocateEconomy(br)
                } else {
                    throw MalformedDataException()
                }
            } catch (e: MalformedDataException) {
                println("Skipping malformed line of input")
            }
        }
    } */

    @Throws(IOException::class, MalformedDataException::class, AeroplaneFullException::class)
    private fun allocateCrew(br: BufferedReader) {
        val firstName = readStringValue(br)
        val lastName = readStringValue(br)
        val crew: Passenger = Crew(firstName, lastName)

        allocateInRange(
            crew,
            Seat(Seat.Companion.CREW_ROW, Seat.Companion.SMALLEST_LETTER),
            Seat(Seat.Companion.CREW_ROW, Seat.Companion.BIGGEST_LETTER)
        )
        //       create a crew member using firstName and lastName
        //       call allocateInRange with appropriate arguments
    }

    @Throws(IOException::class, MalformedDataException::class, AeroplaneFullException::class)
    private fun allocateBusiness(br: BufferedReader) {
        val firstName = readStringValue(br)
        val lastName = readStringValue(br)
        val age = readIntValue(br)
        val luxury = readLuxuryValue(br)

        val businessP: Passenger = BusinessClass(firstName, lastName, age, luxury)

        allocateInRange(
            businessP, Seat(Seat.Companion.BUSINESS_FIRST_ROW, Seat.Companion.SMALLEST_LETTER),
            Seat(Seat.Companion.BUSINESS_LAST_ROW, Seat.Companion.BIGGEST_LETTER)
        )
        //       create a business class passenger using firstName, lastName, age and luxury
        //       call allocateInRange with appropriate arguments
    }

    @Throws(IOException::class, MalformedDataException::class, AeroplaneFullException::class)
    private fun allocateEconomy(br: BufferedReader) {
        val firstName = readStringValue(br)
        val lastName = readStringValue(br)
        val age = readIntValue(br)

        val economyP: Passenger = EconomyClass(firstName, lastName, age)

        allocateInRange(
            economyP, Seat(Seat.Companion.ECONOMY_FIRST_ROW, Seat.Companion.SMALLEST_LETTER),
            Seat(Seat.Companion.ECONOMY_LAST_ROW, Seat.Companion.BIGGEST_LETTER)
        )

        //       create an economy class passenger using firstName, lastName and age
        //       call allocateInRange with appropriate arguments
    }

    @Throws(AeroplaneFullException::class)
    fun upgrade() {
        var current: Seat? = Seat(Seat.Companion.BUSINESS_FIRST_ROW, Seat.Companion.SMALLEST_LETTER)
        val last = Seat(Seat.Companion.BUSINESS_LAST_ROW, Seat.Companion.BIGGEST_LETTER)
        var isLast = false

        while ((current!!.hasNext() || current == last) && !isLast && (firstOcuppiedEconomySeat() != null)) {
            if (!isOccupiedBusinessSeats(current)) {
                val fSeat = firstOcuppiedEconomySeat()
                allocateInRange(allocation[fSeat], current, last)
                allocation.remove(fSeat)
            }
            if (current == last) {
                isLast = true
            } else {
                current = current.next()
            }
        }
    }

    private fun isOccupiedBusinessSeats(seat: Seat?): Boolean {
        return allocation.containsKey(seat)
    }

    private fun firstOcuppiedEconomySeat(): Seat? {
        val isFound = false
        var current: Seat? = Seat(Seat.Companion.ECONOMY_FIRST_ROW, Seat.Companion.SMALLEST_LETTER)
        val last = Seat(Seat.Companion.ECONOMY_LAST_ROW, Seat.Companion.BIGGEST_LETTER)
        var isLast = false

        while (!isFound && (current!!.hasNext() || current == last) && !isLast) {
            if (allocation.containsKey(current)) {
                return current
            }
            if (current == last) {
                isLast = true
            } else {
                current = current.next()
            }
        }

        return null
    }

    companion object {
        private const val CREW = "crew"
        private const val BUSINESS = "business"
        private const val ECONOMY = "economy"

        @Throws(MalformedDataException::class, IOException::class)
        private fun readStringValue(br: BufferedReader): String {
            val result = br.readLine() ?: throw MalformedDataException()

            return result
        }

        @Throws(MalformedDataException::class, IOException::class)
        private fun readIntValue(br: BufferedReader): Int {
            try {
                return readStringValue(br).toInt()
            } catch (e: NumberFormatException) {
                throw MalformedDataException()
            }
        }

        @Throws(MalformedDataException::class, IOException::class)
        private fun readLuxuryValue(br: BufferedReader): Luxury {
            try {
                return Luxury.valueOf(readStringValue(br))
            } catch (e: IllegalArgumentException) {
                throw MalformedDataException()
            }
        }
    }
}
