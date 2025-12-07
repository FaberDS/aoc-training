/**
 * Advent of code (2025) solution for day 7 by Denis Schüle.
 * https://adventofcode.com/2025/day/7
 */
import aoc.AoCDay
import datastructures.CharGridFromString
import datastructures.Coord
import extensions.toDebugStr
import extensions.toDebugString
import utils.println
import utils.splitIntoCharGrid
import utils.splitIntoStringGrid

/**
 *   PART-1
 *   // TODO
 *You quickly locate a diagram of the tachyon manifold (your puzzle input). A tachyon beam enters the manifold at the location marked S; tachyon beams always move downward. Tachyon beams pass freely through empty space (.). However, if a tachyon beam encounters a splitter (^), the beam is stopped; instead, a new tachyon beam continues from the immediate left and from the immediate right of the splitter.
 * .......S.......
 * .......|.......
 * ......|^|......
 * ......|.|......
 * .....|^|^|.....
 * .....|.|.|.....
 * ....|^|^|^|....
 * ....|.|.|.|....
 * ...|^|^|||^|...
 * ...|.|.|||.|...
 * ..|^|^|||^|^|..
 * ..|.|.|||.|.|..
 * .|^|||^||.||^|.
 * .|.|||.||.||.|.
 * |^|^|^|^|^|||^|
 * |.|.|.|.|.|||.|
 *
 *   PART-2
 *   // TODO
 *
 */
typealias CharGrid = MutableList<MutableList<Char>>
class TreeNode(val coord:Coord) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}
fun Coord.inRange(maxCol:Int, maxRow:Int):Boolean =if( row in 0..maxRow && col in 0..maxCol) true else false
object Day07 : AoCDay<Long>(
    year = 2025,
    day = 7,
    // TODO: fill these once you know them
    example1Expected = 21,
    example2Expected = 40,
    answer1Expected = 1581,
    answer2Expected = null,
) {

    override fun part1(rows: List<String>): Long {
        var result = 0L
        var startCoords = Coord(0,rows.first().indexOfFirst { it == 'S' })
        val grid = splitIntoCharGrid(rows.drop(1))
        val ignoreColumns = mutableSetOf<Int>()
        val checkColumnsForSplit = mutableSetOf<Int>()
        checkColumnsForSplit.add(startCoords.col)
//        println("startCoords: $startCoords")
        for((y,row) in grid.withIndex()) {
            val foundSplitsInColumn = mutableSetOf<Int>()
//            println("y: ${y.toString().padStart(5)} checkColumnsForSplit: $checkColumnsForSplit")
            for( col in checkColumnsForSplit){
                if(ignoreColumns.contains(col)) {
//                    println("ignoreColum $col is ignored")
                    continue
                }
                val possibleSplit = row.get(col)
                if(possibleSplit == '^') {
                    foundSplitsInColumn.add(col)
                    result++
//                    println("foundSplit at y: ${y.toString().padStart(5)} x: ${col.toString().padStart(5)} | result: $${
//                        result.toString().padStart(5)
//                    } | row: $${row}")
                } else {
                    grid[y][col] = '|'
                }
            }
            if(foundSplitsInColumn.isNotEmpty()) {
//                println("foundSplitsInColumn $foundSplitsInColumn | ${foundSplitsInColumn.size} | checkColumnsForSplit: ${checkColumnsForSplit.size}")
                for(foundCol in foundSplitsInColumn) {
                    checkColumnsForSplit.remove(foundCol)
                    checkColumnsForSplit.add(foundCol-1)
                    checkColumnsForSplit.add(foundCol+1)
                    grid[y][foundCol-1] = '|'
                    grid[y][foundCol+1] = '|'
                }
            }

//            println("GRID: \n${grid.toDebugStr()}")
        }
//        println("GRID: \n${grid.toDebugStr()}")
        return result
    }

    val PATH_SEPARATOR = '-'
    val COORD_SEPARATOR = ':'

    fun toCoordString(coord: Coord): String = toCoordString(coord.row, coord.col)
    fun toCoordString(y:Int,x:Int):String  = "${y.toString().padStart(3,'0')}$COORD_SEPARATOR${x.toString().padStart(3, '0')}"

    fun addCoordToString(basePath: String,y: Int,x:Int): String = basePath + PATH_SEPARATOR + toCoordString(y,x)
    fun getLastCoordOfPath(path:String):Coord? {
        if(path.isEmpty()) return null
        val last = path.split(PATH_SEPARATOR).last()
        val coords = last.split(COORD_SEPARATOR)
        val y = coords.first().toInt()
        val x = coords.last().toInt()
        return Coord(y,x)
    }

    override fun part2(rows: List<String>): Long {
        var startCoords = Coord(0,rows.first().indexOfFirst { it == 'S' })
        val grid = splitIntoCharGrid(rows.drop(1))

        var pathStart = Coord(startCoords.row +1,startCoords.col)
        val possiblePaths = mutableListOf<String>()
        possiblePaths.add(toCoordString(pathStart))
        println("path $pathStart | possiblePaths.size: ${possiblePaths.size} possiblePaths: $possiblePaths")
        val maxY = grid.size -1
        var i = 0
        val completedPaths = mutableSetOf<String>()
        var completedPathsCountLastRound = 0
        var possiblePathsCountLastRound = 0
        while(possiblePaths.isNotEmpty()) {
            var lastChangedI = 0
            var pathsToUpdate = mutableSetOf<String>()
            var completedPathInIteration = mutableSetOf<String>()
            for((pi, path) in possiblePaths.withIndex()) {

                val lastSplit = getLastCoordOfPath(path) ?: continue

                val lastSplitY = lastSplit.row
                val lastSplitX = lastSplit.col

                var optionLeft: Coord? = Coord(lastSplitY,lastSplitX -1)
                var optionRight: Coord?  = Coord(lastSplitY,lastSplitX +1)
//                println("lastSplitY: ${lastSplitY} | maxY: ${maxY}")

                for(y in 0..maxY) {
//                    println("y: ${y.toString().padStart(3)} | GRID: ${grid[y]}")
                    if(y < ( lastSplitY)) continue
                    if(y == 3) {
                        println("y: $y")
                    }
//                    println("y: ${y.toString().padStart(3)} | optionLeft.col: ${optionLeft?.col} | optionRight.col: ${optionRight?.col}")
                    if(optionLeft != null && grid[y][optionLeft.col] == '^') {
                        grid[y][optionLeft.col+1] = '|'
                        grid[y][optionLeft.col-1] = '|'
                        val updatedPath = addCoordToString(path,y,optionLeft.col)
//                        println("path: $path | updatedPath: $updatedPath")
                        pathsToUpdate.add(updatedPath)
                        optionLeft = null
                    }
                    if(optionRight != null &&grid[y][optionRight.col] == '^') {
                        grid[y][optionRight.col+1] = '|'
                        grid[y][optionRight.col-1] = '|'
                        val updatedPath = addCoordToString(path,y,optionRight.col)
//                        println("path: $path | updatedPath: $updatedPath")
                        pathsToUpdate.add(updatedPath)
                        optionRight = null
                    }
                }

//                println("PI: ${pi.toString().padStart(3)} | lastSplit: ${lastSplit} " +
//                        "\n -> optionLeft: $optionLeft" +
//                        "\n -> optionRight: $optionRight"
//                )
                if(optionLeft != null) {
                    val updatedPath = addCoordToString(path,maxY,optionLeft.col)
//                    println("path: $path | updatedPath: $updatedPath")
                    completedPathInIteration.add(updatedPath)

                }
                if(optionRight != null) {
                    val updatedPath = addCoordToString(path,maxY,optionRight.col)
//                    println("path: $path | updatedPath: $updatedPath")
                    completedPathInIteration.add(updatedPath)

                }
            }
            for((key,value) in completedPathInIteration.withIndex()) {
//                println("COMPLETED-> ${key.toString().padStart(3)} | value: $value")
                possiblePaths.removeAll {it.startsWith(value) }

            }
//            println(grid.toDebugStr())
            for((key,value) in pathsToUpdate.withIndex()) {
                var updatedPath = false
                for((pi,path) in possiblePaths.withIndex()) {
                    if(value.startsWith(path)){
                        possiblePaths[pi] = value
                        updatedPath = true
                        break
                    }
                }
                if(!updatedPath) {
                    possiblePaths.add(value)
                }
//                println("UPDATE AFTER: key: ${key.toString().padStart(3)} | possiblePaths: $possiblePaths")
            }
            completedPaths.addAll(completedPathInIteration)
            if(i == 100) break
            i++
            if(completedPathsCountLastRound == completedPaths.size && possiblePathsCountLastRound == possiblePaths.size) {
                break
            }
            completedPathsCountLastRound = completedPaths.size
            possiblePathsCountLastRound = possiblePaths.size

            println("i: ${i.toString().padStart(3)} | completedPaths: ${completedPaths.size} | possiblePaths: ${possiblePaths.size}")
        }
        println("FINAL:")
        for((i,path) in completedPaths.withIndex()) {
            println("${i.toString().padStart(3)} | $path")
        }
        return completedPaths.size.toLong()
    }
//    override fun part2(rows: List<String>): Long {
//        var result = 0L
//        var startCoords = Coord(0,rows.first().indexOfFirst { it == 'S' })
//        val grid = splitIntoCharGrid(rows.drop(1))
//
//        var pathStart = Coord(startCoords.row +1,startCoords.col)
//        val possiblePaths = mutableListOf<String>()
//        possiblePaths.add(toCoordString(pathStart))
//        println("path $pathStart | possiblePaths.size: ${possiblePaths.size} possiblePaths: $possiblePaths")
//        val maxY = grid.size -1
//        var i = 0
//        val completedPaths = mutableSetOf<String>()
//        while(true) {
//            var lastChangedI = 0
//            var pathsToUpdate = mutableSetOf<String>()
//            var completedPathInIteration = mutableSetOf<String>()
//            for((pi, path) in possiblePaths.withIndex()) {
//
//                val lastSplit = getLastCoordOfPath(path) ?: continue
//
//                val lastSplitY = lastSplit.row
//                val lastSplitX = lastSplit.col
//
//                var optionLeft: Coord? = Coord(lastSplitY,lastSplitX -1)
//                var optionRight: Coord?  = Coord(lastSplitY,lastSplitX +1)
////                println("lastSplitY: ${lastSplitY} | maxY: ${maxY}")
//
//                for(y in 0..maxY) {
////                    println("y: ${y.toString().padStart(3)} | GRID: ${grid[y]}")
//                    if(y < ( lastSplitY)) continue
//                    if(y == 3) {
//                        println("y: $y")
//                    }
////                    println("y: ${y.toString().padStart(3)} | optionLeft.col: ${optionLeft?.col} | optionRight.col: ${optionRight?.col}")
//                    if(optionLeft != null && grid[y][optionLeft.col] == '^') {
//                        grid[y][optionLeft.col+1] = '|'
//                        grid[y][optionLeft.col-1] = '|'
//                        val updatedPath = addCoordToString(path,y,optionLeft.col)
////                        println("path: $path | updatedPath: $updatedPath")
//                        pathsToUpdate.add(updatedPath)
//                        optionLeft = null
//                    }
//                    if(optionRight != null &&grid[y][optionRight.col] == '^') {
//                        grid[y][optionRight.col+1] = '|'
//                        grid[y][optionRight.col-1] = '|'
//                        val updatedPath = addCoordToString(path,y,optionRight.col)
////                        println("path: $path | updatedPath: $updatedPath")
//                        pathsToUpdate.add(updatedPath)
//                        optionRight = null
//                    }
//                }
//
////                println("PI: ${pi.toString().padStart(3)} | lastSplit: ${lastSplit} " +
////                        "\n -> optionLeft: $optionLeft" +
////                        "\n -> optionRight: $optionRight"
////                )
//                if(optionLeft != null) {
//                    val updatedPath = addCoordToString(path,maxY,optionLeft.col)
////                    println("path: $path | updatedPath: $updatedPath")
//                    completedPathInIteration.add(updatedPath)
//
//                }
//                if(optionRight != null) {
//                    val updatedPath = addCoordToString(path,maxY,optionRight.col)
////                    println("path: $path | updatedPath: $updatedPath")
//                    completedPathInIteration.add(updatedPath)
//
//                }
//            }
//            for((key,value) in completedPathInIteration.withIndex()) {
////                println("COMPLETED-> ${key.toString().padStart(3)} | value: $value")
//                possiblePaths.removeAll {it.startsWith(value) }
//
//            }
////            println(grid.toDebugStr())
//            for((key,value) in pathsToUpdate.withIndex()) {
//                var updatedPath = false
//                for((pi,path) in possiblePaths.withIndex()) {
//                    if(value.startsWith(path)){
//                        possiblePaths[pi] = value
//                        updatedPath = true
//                        break
//                    }
//                }
//                if(!updatedPath) {
//                    possiblePaths.add(value)
//                }
////                println("UPDATE AFTER: key: ${key.toString().padStart(3)} | possiblePaths: $possiblePaths")
//            }
//            completedPaths.addAll(completedPathInIteration)
//            if(i == 100) break
//            i++
//        }
//        println("FINAL:")
//        for((i,path) in completedPaths.withIndex()) {
//            println("${i.toString().padStart(3)} | $path")
//        }
//        return completedPaths.size.toLong()
//    }

}

// -------- manual runner --------

fun main() =
    Day07.run(
        runExamples = true,
        runReal = true,
        runPart1 = false,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
