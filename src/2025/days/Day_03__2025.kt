/**
 * Advent of code (2025) solution for day 3 by Denis Schüle.
 * [Advent of code 2025-3 ](https://adventofcode.com/2025/day/3)
 **/
import aoc.handleSubmit
import com.sun.org.apache.xalan.internal.lib.ExsltMath.highest
import extensions.firstElement
import extensions.printSeparated
import extensions.removeFirstElement
import kotlin.collections.first
import kotlin.time.measureTimedValue


fun main() {
    /**
     * PART-1
     * There are batteries nearby that can supply emergency power to the escalator for just such an occasion. The batteries are each labeled with their joltage rating, a value from 1 to 9. You make a note of their joltage ratings (your puzzle input). For example:
     *
     * 987654321111111
     * 811111111111119
     * 234234234234278
     * 818181911112111
     * The batteries are arranged into banks; each line of digits in your input corresponds to a single bank of batteries. Within each bank, you need to turn on exactly two batteries; the joltage that the bank produces is equal to the number formed by the digits on the batteries you've turned on. For example, if you have a bank like 12345 and you turn on batteries 2 and 4, the bank would produce 24 jolts. (You cannot rearrange batteries.)
     *
     * You'll need to find the largest possible joltage each bank can produce. In the above example:
     *
     * In 987654321111111, you can make the largest joltage possible, 98, by turning on the first two batteries.
     * In 811111111111119, you can make the largest joltage possible by turning on the batteries labeled 8 and 9, producing 89 jolts.
     * In 234234234234278, you can make 78 by turning on the last two batteries (marked 7 and 8).
     * In 818181911112111, the largest joltage you can produce is 92.
     * The total output joltage is the sum of the maximum joltage from each bank, so in this example, the total output joltage is 98 + 89 + 78 + 92 = 357.
     *
     *
     */
     val config = ConfigForDay(
             submit1 = true,
             submit2 = false,
             check1 = false,
             check2 = false,
             checkDemo1 = false,
             checkDemo2 = false,
             execute1 = true,
             execute2 = false,
             execute1demo = true,
             execute2demo = false,
             exampleSolution1 = 357,
             exampleSolution2 = 0,
             solution1 = 0,
             solution2 = 0)
    fun twoHighestInOrder(nums: List<Int>): List<Pair<Int,Int>> {
        var firstIdx = -1
        var secondIdx = -1

        for ((idx, v) in nums.withIndex()) {
            if (firstIdx == -1 || v > nums[firstIdx]) {
                // shift the old max to second
                secondIdx = firstIdx
                firstIdx = idx
            } else if (idx != firstIdx && (secondIdx == -1 || v > nums[secondIdx])) {
                secondIdx = idx
            }
        }
        if (secondIdx == -1) return listOf(nums[firstIdx] to firstIdx)
        return if (firstIdx < secondIdx)
            listOf(nums[firstIdx] to firstIdx, nums[secondIdx] to secondIdx)
        else
            listOf(nums[secondIdx] to secondIdx, nums[firstIdx] to firstIdx)
    }

    fun findHighestJoltage(row: String): Int {
        val digits = row.map { it.digitToInt() }
        val topTwo = twoHighestInOrder(digits)
        var h = topTwo.first()
        var highest = h.first
        var s = topTwo[1]
        var second = s.first
        println("Highest Joltage: $highest $second | s.first > h.first: ${s.first > h.first}")
        val splitPoint = s.second + 1
        if (s.first > h.first && splitPoint < row.length) {
            val secondString = row.substring(splitPoint)
            val three = secondString.map { it.digitToInt() }.sortedDescending().first()
            println("secondString: $secondString | three: $three")

            second = three
            highest = s.first
        }
        val joltage =  "$highest$second".toInt()
        println("joltage: $joltage | l: $topTwo | row: $row | digits: $digits")

        return joltage
    }
    fun part1(input: List<String>): Int {
        return input
            .sumOf{findHighestJoltage(it)}
    }


    fun part2(input: List<String>): Int {
        var result = 0
        return result
    }

    try {
        val exampleInput1 = readInput("day_03_demo", "2025")
        val exampleInput2 = readInput("day_03_demo", "2025")
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
                val input = readInput("day_03", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1)
            if(config.submit1) handleSubmit(2025,3,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_03", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == config.solution2)
            if(config.submit2) handleSubmit(2025,3,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}