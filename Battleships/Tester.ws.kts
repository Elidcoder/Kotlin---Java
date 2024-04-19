val x = 'B' - 'A'
println(x as Int)
val myGrid = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4), mutableListOf(5, 6), mutableListOf(7, 8))

fun increase(){
    for (i in myGrid) {
        for (j in 0..<myGrid[0].size) {
            i[j] = i[j] + 1
        }
    }
}

val output: Array<Array<Int?>> = arrayOf(emptyArray(), emptyArray())
print(output.toString())
increase()
print(myGrid)
print(myGrid.lastIndex)
println(myGrid[0].lastIndex)