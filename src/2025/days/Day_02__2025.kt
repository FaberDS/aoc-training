/**
 * Advent of code (2025) solution for day 2 by Denis Schüle.
 * [Advent of code 2025-2 ](https://adventofcode.com/2025/day/2)
 **/
import aoc.handleSubmit
import extensions.printSeparated
import extensions.splitInHalf
import javax.swing.text.html.HTML.Attribute.N
import kotlin.math.floor
import kotlin.time.measureTimedValue


//    (The ID ranges are wrapped here for legibility; in your input, they appear on a single long line.)
//    The ranges are separated by commas (,); each range gives its first ID and last ID separated by a dash (-).
//    Since the young Elf was just doing silly patterns, you can find the invalid IDs by looking for any ID which is made
//    only of some sequence of digits repeated twice. So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be invalid IDs.
//    None of the numbers have leading zeroes; 0101 isn't an ID at all. (101 is a valid ID that you would ignore.)
//    Your job is to find all of the invalid IDs that appear in the given ranges. In the above example:
//    11-22 has two invalid IDs, 11 and 22.
//    95-115 has one invalid ID, 99.
//    998-1012 has one invalid ID, 1010.
//    1188511880-1188511890 has one invalid ID, 1188511885.
//    222220-222224 has one invalid ID, 222222.
//    1698522-1698528 contains no invalid IDs.
//    446443-446449 has one invalid ID, 446446.
//    38593856-38593862 has one invalid ID, 38593859.
//    The rest of the ranges contain no invalid IDs.
//    Adding up all the invalid IDs in this example produces 1227775554.

fun main() {

     val config = ConfigForDay(
             submit1 = true,
             submit2 = false,
             check1 = false,
             check2 = false,
             checkDemo1 = true,
             checkDemo2 = false,
             execute1 = true,
             execute2 = false,
             execute1demo = false,
             execute2demo = false,
             exampleSolution1 = 1227775554,
             exampleSolution2 = 0,
             solution1 = 0,
             solution2 = 0)

    fun parseRanges(lines: List<String>): MutableList<Pair<Long, Long>> {
        val ranges = mutableListOf<Pair<Long,Long>>()

        for (line in lines) {
            line.split(",").forEach { range ->
                if (!range.isEmpty()){
                    println("RANGE: $range")
                    val (from, to) = range.split('-')
                    ranges.add(Pair(from.toLong(),to.toLong()))
                }
            }
        }
        return ranges
    }

    fun getInvalidNumber(i: Long): Long? {
        val iString = "$i"
        val (first, second) = iString.splitInHalf()
        println("iString: $iString | first: $first, second: $second")
        if (second.length == first.length && second == first) {
            return iString.toLong()
        }
        return null
    }
    fun findInvalidRanges(range: Pair<Long, Long>): List<Long> {
        val invalidRanges = mutableListOf<Long>()
        val lower = range.first
        val upper = range.second

        for (i in lower..upper) {
            val invalidNumber = getInvalidNumber(i)
            if (invalidNumber != null) {
                invalidRanges.add(invalidNumber)
            }
        }
        println("$lower - $upper | invalid: $invalidRanges")

        return invalidRanges
    }

    fun part1(input: List<String>): Long {
        println("🙈MAX INT: ${Int.MAX_VALUE}")
        val ranges = parseRanges(input)
        var result: Long = 0
        ranges.forEach {
            val invalidRanges = findInvalidRanges(it)
            invalidRanges.forEach {
                result += it
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        return result
    }

    try {
        val exampleInput1 = readInput("day_02_demo", "2025")
        val exampleInput2 = readInput("day_02_demo", "2025")
        /* --- TEST DEMO INPUT --- */
        if(config.execute1demo) {
            val part1DemoSolution = part1(exampleInput1)
            "Part 1 Demo".printSeparated()
            println("- Part 1 Demo: $part1DemoSolution")
            if(config.checkDemo1) check(part1DemoSolution == config.exampleSolution1.toLong())
        }

        if(config.execute2demo) {
            "Part 2 Demo".printSeparated()
            val part2DemoSolution = part2(exampleInput2)
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == config.exampleSolution2)
        }

        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_02", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution.toString() == config.solution1.toString())
            if(config.submit1) handleSubmit(2025,2,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_02", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == config.solution2)
            if(config.submit2) handleSubmit(2025,2,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}