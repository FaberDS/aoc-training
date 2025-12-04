/**
 * Advent of code (2025) solution for day 4 by Denis Schüle.
 * [Advent of code 2025-3 ](https://adventofcode.com/2025/day/4)
 **/
import aoc.handleSubmit
import com.sun.org.apache.xalan.internal.lib.ExsltMath.highest
import extensions.findMaxNumberWithNDigits
import extensions.firstElement
import extensions.printSeparated
import extensions.removeFirstElement
import kotlin.collections.first
import kotlin.text.indexOf
import kotlin.time.measureTimedValue


fun main() {
    /**
     * PART-1
    * The rolls of paper (@) are arranged on a large grid; the Elves even have a helpful diagram (your puzzle input)
     * indicating where everything is located.
     *
     * For example:
     *
     * ..@@.@@@@.
     * @@@.@.@.@@
     * @@@@@.@.@@
     * @.@@@@..@.
     * @@.@@@@.@@
     * .@@@@@@@.@
     * .@.@.@.@@@
     * @.@@@.@@@@
     * .@@@@@@@@.
     * @.@.@@@.@.
     *
     * The forklifts can only access a roll of paper if there are fewer than four rolls of paper in the eight adjacent positions.
     * If you can figure out which rolls of paper the forklifts can access, they'll spend less time looking and more time breaking down the wall to the cafeteria.
     *
     * In this example, there are 13 rolls of paper that can be accessed by a forklift (marked with x):
     *
     * ..xx.xx@x.
     * x@@.@.@.@@
     * @@@@@.x.@@
     * @.@@@@..@.
     * x@.@@@@.@x
     * .@@@@@@@.@
     * .@.@.@.@@@
     * x.@@@.@@@@
     * .@@@@@@@@.
     * x.x.@@@.x.
     * Consider your complete diagram of the paper roll locations. How many rolls of paper can be accessed by a forklift?
     *
     *
     */

    /**
     * PART-2

     */
    val config = ConfigForDay(
             submit1 = false,
             submit2 = false,
             check1 = false,
             check2 = false,
             checkDemo1 = true,
             checkDemo2 = false,
             execute1 = false,
             execute2 = false,
             execute1demo = false,
             execute2demo = true,
             exampleSolution1 = 13,
             exampleSolution2 = 0,
             solution1 = 1478,
             solution2 = 0)
    fun hasLessThanNeighbours(grid: List<List<Char>>, n: Int, row: Int,col: Int): Boolean {
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
//                println("hasLessThanNeighbours: row: $row, col: $col, neighbours: $neighbours")
                return false
            }
        }
        return if(neighbours >= n) false else true
    }

    fun isRoll(c: Char): Boolean = c == '@'




    fun part1(input: List<String>): Long {
        val grid = splitIntoCharGrid(input)

        var result = 0L
        for ((rowIndex,row) in grid.withIndex()) {
            for ((colIndex,col) in row.withIndex()) {
                if (!isRoll(col)) continue
                val hasLessThanNeighbours = hasLessThanNeighbours(grid,4,rowIndex,colIndex)
                println("row: $rowIndex ($row), col: $colIndex ($col) | hasLess: ${hasLessThanNeighbours}")
                result += if (hasLessThanNeighbours) 1 else 0
            }
        }

        return result
    }

    fun solveDay4(grid: List<String>, n: Int): Long {
        var result = 0L
        for (row in grid) {
            for (col in row) {

            }
        }

        return 0L

    }
    fun part2(input: List<String>) = solveDay4(input,12)

    try {
        val exampleInput2 = readInput("day_04_demo", "2025")
        /* --- TEST DEMO INPUT --- */
        if(config.execute1demo) {
            val exampleInput1 = readInput("day_04_demo", "2025")
            val part1DemoSolution = part1(exampleInput1)
            "Part 1 Demo".printSeparated()
            println("- Part 1 Demo: $part1DemoSolution")
            if(config.checkDemo1) check(part1DemoSolution == config.exampleSolution1.toLong())
        }

        if(config.execute2demo) {
            "Part 2 Demo".printSeparated()
            val part2DemoSolution = part2(exampleInput2)
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == 0L)
        }

        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_04", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1.toLong())
            if(config.submit1) handleSubmit(2025,4,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_04", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == 0L)
            if(config.submit2) handleSubmit(2025,4,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}