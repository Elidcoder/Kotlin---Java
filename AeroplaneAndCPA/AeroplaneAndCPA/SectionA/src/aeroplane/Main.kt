package aeroplane

import java.io.IOException

object Main {
    @Throws(AeroplaneFullException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 1) {
            println("Program should be invoked with exactly one argument, the name of the data file")
            System.exit(1)
        }

        val allocator = SeatAllocator()

        try {
            allocator.allocate(args[0])
        } catch (e: IOException) {
            println("An IO exception occurred")
            System.exit(1)
        } catch (e: AeroplaneFullException) {
            println("Unable to allocate all passengers")
        }

        println(allocator)

        allocator.upgrade()

        val passengers: MutableSet<Passenger> = HashSet()
        passengers.add(EconomyClass("Hang", "Li Li", 17))
        passengers.add(EconomyClass("Hang", "Li Li", 17))
        passengers.add(Crew("Hang", "Li Li"))
        passengers.add(Crew("Hang", "Li Li"))
        passengers.add(Crew("Hang", "Ling Li"))
        passengers.add(Crew("Hang", "Li"))


        println(countAdults(passengers))
    }

    fun countAdults(passengers: Set<Passenger>): Int {
        var count = 0

        for (passenger in passengers) {
            if (passenger.isAdult) {
                count++
            }
        }

        return count
    }
}
