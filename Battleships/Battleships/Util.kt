

object Util {
    private fun letterToIndex(letter: Char): Int = letter - 'A'

    private fun numberToIndex(number: Char): Int = number - '0'

    fun parseCoordinate(s: String): Coordinate {
        return Coordinate(letterToIndex(s.first()), numberToIndex(s[1]))
    }

    fun hideShip(piece: Piece): Piece
        = if (piece == Piece.SHIP) Piece.WATER else piece

    fun hideShips(grid: Array<Array<Piece>>) {
        for (row in grid){
            for (colNumb in 0..grid[0].lastIndex){
                hideShip(row[colNumb])
            }
        }
    }

    fun deepClone(input: Array<Array<Piece>>): Array<Array<Piece>> {
        val output: Array<Array<Piece>?> = arrayOfNulls(input.size)
        for (i in input.indices) {
            output[i] = input[i].copyOf(input[i].size).requireNoNulls()
        }
        return (output as Array<Array<Piece>>)
    }
}