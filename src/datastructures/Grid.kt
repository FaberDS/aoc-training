package datastructures

open class Grid<T>(
    val grid: MutableList<MutableList<T>>
) {

    val isEmpty: Boolean get() = grid.isEmpty()
    val isNotEmpty: Boolean get() = !isEmpty

    var maxRow: Int = grid.size - 1
    var maxCol: Int = if (grid.isNotEmpty()) grid[0].lastIndex else -1

    fun cellExist(row: Int, col: Int): Boolean =
        row in 0..maxRow && col in 0..maxCol

    fun cellNotExist(row: Int, col: Int): Boolean =
        !cellExist(row, col)

    protected open fun isEmptyValue(value: T): Boolean = false

    fun isCellEmpty(row: Int, col: Int): Boolean {
        if (cellNotExist(row, col)) return false
        return isEmptyValue(grid[row][col])
    }

    fun setCell(row: Int, col: Int, value: T): Boolean {
        if (cellNotExist(row, col)) return false
        grid[row][col] = value
        return true
    }

    fun getCell(row: Int, col: Int): T? =
        if (cellExist(row, col)) grid[row][col] else null
}


class IntGridFromString(input: String) : Grid<Int>(
    input.lines()
        .filter { it.isNotBlank() }
        .map { line ->
            line.map { ch ->
                ch.digitToIntOrNull()
                    ?: error("Non-digit '$ch' in datastructures.IntGridFromString")
            }.toMutableList()
        }.toMutableList()
) {
    override fun isEmptyValue(value: Int): Boolean = value == 0
}

class CharGridFromString(lines: List<String>) : Grid<Char>(
    lines
        .filter { it.isNotBlank() }
        .map { line ->
            line.map { ch ->
                ch
            }.toMutableList()
        }.toMutableList()
) {
    override fun isEmptyValue(value: Char): Boolean = value == '.'

    fun cellValueIsRoll(row: Int, col: Int): Boolean =  getCell(row, col)?.let { it == '@'} ?: false
}
fun CharGridFromString.getRollCells(): Set<Pair<Int, Int>> {
    val result = mutableSetOf<Pair<Int, Int>>()
    grid.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, char ->
            if (char == '@') {
                result += rowIndex to colIndex
            }
        }
    }
    return result
}

fun CharGridFromString.cellsWithLessThanNNeighbours(n: Int): Int {
    val rollCells = getRollCells()
    return rollCells.count { (row, col) ->
        hasLessThanNeighbours(n, row, col)
    }
}

fun CharGridFromString.hasLessThanNeighbours(n: Int, row: Int,col: Int): Boolean {
    var neighbours = 0
    for(adjust in AdjacentPositions.entries) {
        val r = row + adjust.row
        val c = col + adjust.col
        try {
            val isEmpty = grid.get(r).get(c) == '.'
            if(!isEmpty) {
                neighbours++
            }
        } catch (e: Exception) {

        }

        if(neighbours >= n) {
            return false
        }
    }
    return neighbours < n
}

fun CharGridFromString.removeRollsWithLessThanNNeighbours(n: Int): Int {
    var removedRolls = 0
    var canRemove = true
    while(canRemove) {
        val rollCells = getRollCells()
        val currentRemovedRolls = removedRolls
        for(rollCell in rollCells) {
            if(hasLessThanNeighbours(n, rollCell.first, rollCell.second)) {
                setCell(rollCell.first, rollCell.second,'.')
                removedRolls++
            }
        }
        if (currentRemovedRolls == removedRolls) {
            canRemove = false
        }
    }

    return removedRolls
}
