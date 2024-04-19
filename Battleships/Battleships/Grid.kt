class Grid {
    private val WIDTH = 10
    private val HEIGHT = 10
    private val grid = Array(HEIGHT) { Array(WIDTH) {Piece.WATER} }

    fun canPlace(c: Coordinate?, size: Int, isDown: Boolean): Boolean {
        c?.run{
        val coordsToCheck: List<Coordinate> =
            (0..<size).map{if (isDown) {Coordinate(c.row + it, c.column)} else {Coordinate(c.row, c.column + it)}}
        for (coord in coordsToCheck){
            if (grid[coord.row][coord.column] != Piece.WATER){
                return false
            }
        }
        return true} ?: return false
    }

    fun placeShip(c: Coordinate?, size: Int, isDown: Boolean) {
        if (canPlace(c, size, isDown)) {
            assert(c != null)
            val coordsToCheck: List<Coordinate> =
                (0..<size).map{if (isDown) {Coordinate(c!!.row + it, c.column)} else {Coordinate(c!!.row, c.column + it)}}
            for (coord in coordsToCheck){
                grid[coord.row][coord.column] = Piece.SHIP
            }
        }
    }

    fun wouldAttackSucceed(c: Coordinate): Boolean = grid[c.row][c.column] == Piece.SHIP

    fun attackCell(c: Coordinate?) {
        c?.run{
            when (grid[c.row][c.column]){
                Piece.WATER -> {grid[c.row][c.column] = Piece.MISS}
                Piece.SHIP -> {
                    print("Direct Hit!")
                    grid[c.row][c.column] = Piece.DAMAGED_SHIP
                }
                else -> {
                    //leave grid unchanged
                }
            }
        }
    }

    fun areAllSunk(): Boolean = !grid.any{it.contains(Piece.SHIP)}

    fun toPlayerString(): String = getStringRepresentation('.')

    override fun toString(): String {
        return renderGrid(grid)
    }

    private fun renderGrid(inputGrid: Array<Array<Piece>>): String = getStringRepresentation(inputGrid = inputGrid)

    private fun getStringRepresentation(shipDisplay:Char = '#', inputGrid: Array<Array<Piece>> = grid):String{
        val sb = StringBuilder()
        sb.append(" 0123456789\n")
        for (i in inputGrid.indices) {
            sb.append(('A'.code + i).toChar())
            for (j in inputGrid[i].indices) {
                //if (grid[i][j] == null) {
                //    return "!"
                //}
                when (inputGrid[i][j]) {
                    Piece.SHIP -> sb.append(shipDisplay)
                    Piece.DAMAGED_SHIP -> sb.append('*')
                    Piece.MISS -> sb.append('o')
                    Piece.WATER -> sb.append('.')
                }
            }
            sb.append('\n')
        }
        return sb.toString()
    }
}