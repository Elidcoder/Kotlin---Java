import java.util.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var scanner = Scanner(System.`in`)
        var attackAttempts = 0
        var targetSquare: Coordinate
        val computerGrid = makeInitialGrid()
        while (!computerGrid.areAllSunk()) {
            attackAttempts ++
            println(computerGrid.toPlayerString())

            println("Enter a coordinate to attack: ")
            targetSquare = Util.parseCoordinate(readln())

            computerGrid.attackCell(targetSquare)
        }
        println("You took $attackAttempts guesses to sink all the ships")
        print(computerGrid.toPlayerString())
    }

    private fun makeInitialGrid(): Grid {
        val grid = Grid()
        val coords = arrayOf("A7", "B1", "B4", "D3", "F7", "H1", "H4")
        val sizes = intArrayOf(2, 4, 1, 3, 1, 2, 5)
        val isDowns = booleanArrayOf(false, true, true, false, false, true, false)
        for (i in coords.indices) {
            val c = Util.parseCoordinate(coords[i])
            grid.placeShip(c, sizes[i], isDowns[i])
        }
        return grid
    }

    private fun makeRandomGrid(): Grid {
        TODO()
    }
}