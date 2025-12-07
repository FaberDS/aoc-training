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
 *   With your analysis of the manifold complete, you begin fixing the teleporter. However, as you open the side of the teleporter to replace the broken manifold, you are surprised to discover that it isn't a classical tachyon manifold - it's a quantum tachyon manifold.
 *
 * With a quantum tachyon manifold, only a single tachyon particle is sent through the manifold. A tachyon particle takes both the left and right path of each splitter encountered.
 *
 * Since this is impossible, the manual recommends the many-worlds interpretation of quantum tachyon splitting: each time a particle reaches a splitter, it's actually time itself which splits. In one timeline, the particle went left, and in the other timeline, the particle went right.
 *
 * To fix the manifold, what you really need to know is the number of timelines active after a single particle completes all of its possible journeys through the manifold.
 *
 */

object Day07 : AoCDay<Long>(
    year = 2025,
    day = 7,
    example1Expected = 21,
    example2Expected = 40,
    answer1Expected = 1581,
    answer2Expected = 73007003089792,
) {

    override fun part1(rows: List<String>): Long {
        var result = 0L
        var startCoords = Coord(0,rows.first().indexOfFirst { it == 'S' })
        val grid = splitIntoCharGrid(rows.drop(1))
        val ignoreColumns = mutableSetOf<Int>()
        val checkColumnsForSplit = mutableSetOf<Int>()
        checkColumnsForSplit.add(startCoords.col)
        for((y,row) in grid.withIndex()) {
            val foundSplitsInColumn = mutableSetOf<Int>()
            for( col in checkColumnsForSplit){
                if(ignoreColumns.contains(col)) {
                    continue
                }
                val possibleSplit = row.get(col)
                if(possibleSplit == '^') {
                    foundSplitsInColumn.add(col)
                    result++
                } else {
                    grid[y][col] = '|'
                }
            }
            if(foundSplitsInColumn.isNotEmpty()) {
                for(foundCol in foundSplitsInColumn) {
                    checkColumnsForSplit.remove(foundCol)
                    checkColumnsForSplit.add(foundCol-1)
                    checkColumnsForSplit.add(foundCol+1)
                    grid[y][foundCol-1] = '|'
                    grid[y][foundCol+1] = '|'
                }
            }
        }
        return result
    }

    override fun part2(rows: List<String>): Long {
        val startX = rows.first().indexOfFirst { it == 'S' }
        val grid = rows.drop(1).map { it.toCharArray() }
        val maxY = grid.lastIndex
        val maxX = grid[0].lastIndex

        // Key: Coord | Value: Number of completed paths from this point
        val memo = mutableMapOf<Coord, Long>()

        fun countPaths(yStart: Int, xCurrent: Int): Long {
            val currentPos = Coord(yStart, xCurrent)
            if (memo.containsKey(currentPos)) {
                return memo[currentPos]!!
            }

            var count = 0L
            var hasLeftSplit = false
            var hasRightSplit = false

            for (y in yStart..maxY) {

                if (!hasLeftSplit && xCurrent - 1 >= 0) {
                    if (grid[y][xCurrent - 1] == '^') {
                        count += countPaths(y, xCurrent - 1)
                        hasLeftSplit = true
                    }
                }

                if (!hasRightSplit && xCurrent + 1 <= maxX) {
                    if (grid[y][xCurrent + 1] == '^') {
                        count += countPaths(y, xCurrent + 1)
                        hasRightSplit = true
                    }
                }

                if (hasLeftSplit && hasRightSplit) break
            }

            // Split went through until end of grid
            if (!hasLeftSplit && xCurrent - 1 >= 0) {
                count++
            }
            if (!hasRightSplit && xCurrent + 1 <= maxX) {
                count++
            }

            memo[currentPos] = count

            return count
        }
        return countPaths(0, startX)
    }
}

// -------- manual runner --------

fun main() =
    Day07.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
