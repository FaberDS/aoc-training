/**
 * Advent of code (2025) solution for day 7 by Denis Schüle.
 * https://adventofcode.com/2025/day/7
 */
import aoc.AoCDay
import datastructures.Coord
import extensions.toDebugStr
import extensions.toDebugString
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

fun Coord.inRange(maxCol:Int, maxRow:Int):Boolean =if( row in 0..maxRow && col in 0..maxCol) true else false
object Day07 : AoCDay<Long>(
    year = 2025,
    day = 7,
    // TODO: fill these once you know them
    example1Expected = 21,
    example2Expected = null,
    answer1Expected = 1581,
    answer2Expected = null,
) {

    override fun part1(rows: List<String>): Long {
        var result = 0L
        var startCoords = Coord(0,rows.first().indexOfFirst { it == 'S' })
        val grid = splitIntoCharGrid(rows.drop(1))
        val maxX = grid.size - 1
        val maxY = grid.first().size - 1
        val ignoreColums = mutableSetOf<Int>()
        val checkColumnsForSplit = mutableSetOf<Int>()
        checkColumnsForSplit.add(startCoords.col)
//        println("startCoords: $startCoords")
        for((y,row) in grid.withIndex()) {
            val foundSplitsInColumn = mutableSetOf<Int>()
//            println("y: ${y.toString().padStart(5)} checkColumnsForSplit: $checkColumnsForSplit")
            for( col in checkColumnsForSplit){
                if(ignoreColums.contains(col)) {
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

    override fun part2(input: List<String>): Long {
        var result = 0L
        // TODO
        return result
    }
}

// -------- manual runner --------

fun main() =
    Day07.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = false,
        submit1 = true,
        submit2 = false,
    )
