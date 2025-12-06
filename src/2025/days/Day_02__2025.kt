/**
 * Advent of code (2025) solution for day 2 by Denis Schüle.
 * [Advent of code 2025-2 ](https://adventofcode.com/2025/day/2)
 **/
import Setup.ConfigForDay
import aoc.handleSubmit
import extensions.isInvalidId
import extensions.printSeparated
import extensions.splitInHalf
import utils.readInput
import kotlin.time.measureTimedValue

//    PART- 1
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


//    PART-2
//    11-22 still has two invalid IDs, 11 and 22.
//    95-115 now has two invalid IDs, 99 and 111.
//    998-1012 now has two invalid IDs, 999 and 1010.
//    1188511880-1188511890 still has one invalid ID, 1188511885.
//    222220-222224 still has one invalid ID, 222222.
//    1698522-1698528 still contains no invalid IDs.
//    446443-446449 still has one invalid ID, 446446.
//    38593856-38593862 still has one invalid ID, 38593859.
//    565653-565659 now has one invalid ID, 565656.
//    824824821-824824827 now has one invalid ID, 824824824.
//    2121212118-2121212124 now has one invalid ID, 2121212121.
//    Adding up all the invalid IDs in this example produces 4174379265.

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
        execute1demo = true,
        execute2demo = true,
        exampleSolution1 = "1227775554",
        exampleSolution2 = "4174379265",
        solution1 = "38158151648",
        solution2 = "45283684555"
    )

    fun parseRanges(lines: List<String>): MutableList<Pair<Long, Long>> {
        val ranges = mutableListOf<Pair<Long,Long>>()

        for (line in lines) {
            line.split(",").forEach { range ->
                if (!range.isEmpty()){
                    val (from, to) = range.split('-')
                    ranges.add(Pair(from.toLong(),to.toLong()))
                }
            }
        }
        return ranges
    }

    fun getInvalidNumber(i: Long,twiceOccurance: Boolean): Long? {
        if(twiceOccurance){
            val iString = "$i"
            val (first, second) = iString.splitInHalf()
            if (second.length == first.length && second == first) {
                return iString.toLong()
            }
            return null
        }
        if(i.isInvalidId()) return i
        return null
    }

    fun findInvalidRanges(range: Pair<Long, Long>,twiceOccurance: Boolean): List<Long> {
        val invalidRanges = mutableListOf<Long>()
        val lower = range.first
        val upper = range.second

        for (i in lower..upper) {
            val invalidNumber = getInvalidNumber(i,twiceOccurance)
            if (invalidNumber != null) {
                invalidRanges.add(invalidNumber)
            }
        }
        return invalidRanges
    }

    fun part1(input: List<String>): String {
//        utils.println("🙈MAX INT: ${Int.MAX_VALUE}")
        val ranges = parseRanges(input)
        var result: Long = 0
        ranges.forEach {
            val invalidRanges = findInvalidRanges(it,twiceOccurance=true)
            invalidRanges.forEach {
                result += it
            }
        }
        return result.toString()
    }

    fun part2(input: List<String>): String {
        val ranges = parseRanges(input)
        var result: Long = 0
        ranges.forEach {
            val invalidRanges = findInvalidRanges(it,twiceOccurance=false)
            invalidRanges.forEach {
                result += it
            }
        }
        return result.toString()
    }

    try {
        val exampleInput1 = readInput("day_02_demo", "2025")
        val exampleInput2 = readInput("day_02_demo", "2025")
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
            if(config.check1) check(part1Solution== config.solution1)
            if(config.submit1) handleSubmit(2025,2,1,part1Solution)
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_02", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == config.solution2)
            if(config.submit2) handleSubmit(2025,2,2,part2Solution)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}