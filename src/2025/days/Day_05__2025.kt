/**
 * Advent of code (2025) solution for day 5 by Denis Schüle.
 * [Advent of code 2025-5 ](https://adventofcode.com/2025/day/5)
 **/
import aoc.handleSubmit
import datastructures.ConfigForDay
import extensions.printSeparated
import utils.readInput
import kotlin.time.measureTimedValue

/**
 * Part-1
 * The database operates on ingredient IDs. It consists of a list of fresh ingredient ID ranges, a blank line, and a list of available ingredient IDs. For example:
 *
 * 3-5
 * 10-14
 * 16-20
 * 12-18
 *
 * 1
 * 5
 * 8
 * 11
 * 17
 * 32
 * The fresh ID ranges are inclusive: the range 3-5 means that ingredient IDs 3, 4, and 5 are all fresh. The ranges can also overlap; an ingredient ID is fresh if it is in any range.
 *
 * The Elves are trying to determine which of the available ingredient IDs are fresh. In this example, this is done as follows:
 *
 * Ingredient ID 1 is spoiled because it does not fall into any range.
 * Ingredient ID 5 is fresh because it falls into range 3-5.
 * Ingredient ID 8 is spoiled.
 * Ingredient ID 11 is fresh because it falls into range 10-14.
 * Ingredient ID 17 is fresh because it falls into range 16-20 as well as range 12-18.
 * Ingredient ID 32 is spoiled.
 * So, in this example, 3 of the available ingredient IDs are fresh.
 *
 *
 */

fun main() {

     val config = ConfigForDay(
         submit1 = false,
         submit2 = false,
         check1 = true,
         check2 = true,
         checkDemo1 = true,
         checkDemo2 = true,
         execute1 = true,
         execute2 = true,
         execute1demo = false,
         execute2demo = true,
         exampleSolution1 = 3,
         exampleSolution2 = 14,
         solution1 = 756,
         solution2 = 0
     )

    fun isIdValid(id: Long, validRanges: Set<Pair<Long,Long>> ): Boolean {
        for (range in validRanges) {
            if (id in range.first..range.second) { return true}
        }
        return false
    }
    fun part1(lines: List<String>): Int {
        var validIds = 0
        val validRanges = mutableSetOf<Pair<Long,Long>>()
        var readRanges = true
        for (line in lines) {
            if (line.isEmpty()) {
                readRanges = false
                continue
            }
            if (readRanges) {
                val splited = line.split("-")
                validRanges += Pair(splited[0].toLong(), splited[1].toLong())

            } else {
                val id = line.toLong()
                validIds += if(isIdValid(id,validRanges)) 1 else 0
            }
        }
        return validIds
    }

    fun part2(lines: List<String>): Long {
        var rootNode: RangeNode? = null
        for (line in lines) {
            if (line.isEmpty()) {
                break
            }
            val splited = line.split("-")
            val rangeNode = RangeNode(splited[0].toLong(), splited[1].toLong())
            if(rootNode == null) {
                rootNode = rangeNode
            } else {
                rootNode.insertNode(rangeNode)
            }
        }
//        utils.println(rootNode)
//        rootNode?.printTree()
        val validCount = rootNode?.sumValidIds()
        return validCount ?: 0
    }

    try {
        val exampleInput1 = readInput("day_05_demo", "2025")
        val exampleInput2 = readInput("day_05_demo", "2025")
        /* --- TEST DEMO INPUT --- */
        if(config.execute1demo) {
            val part1DemoSolution = part1(exampleInput1)
            "Part 1 Demo".printSeparated()
            println("- Part 1 Demo: $part1DemoSolution")
            if(config.checkDemo1) check(part1DemoSolution == config.exampleSolution1)
        }

        if(config.execute2demo) {
            "Part 2 Demo".printSeparated()
            val part2DemoSolution = part2(exampleInput2)
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == config.exampleSolution2.toLong())
        }

        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_05", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1)
            if(config.submit1) handleSubmit(2025,5,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_05", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == 355555479253787)
            if(config.submit2) handleSubmit(2025,5,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}