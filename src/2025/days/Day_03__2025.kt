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
import kotlin.text.indexOf
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

    /**
     * PART-2
     * The joltage output for the bank is still the number formed by the digits of the batteries you've turned on; the only difference is that now there will be 12 digits in each bank's joltage output instead of two.
     *
     * Consider again the example from before:
     *
     * 987654321111111
     * 811111111111119
     * 234234234234278
     * 818181911112111
     * Now, the joltages are much larger:
     *
     * In 987654321111111, the largest joltage can be found by turning on everything except some 1s at the end to produce 987654321111.
     * In the digit sequence 811111111111119, the largest joltage can be found by turning on everything except some 1s, producing 811111111119.
     * In 234234234234278, the largest joltage can be found by turning on everything except a 2 battery, a 3 battery, and another 2 battery near the start to produce 434234234278.
     * In 818181911112111, the joltage 888911112111 is produced by turning on everything except some 1s near the front.
     * The total output joltage is now much larger: 987654321111 + 811111111119 + 434234234278 + 888911112111 = 3121910778619.
     */
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
             exampleSolution1 = 357,
             exampleSolution2 = 0,
             solution1 = 17193,
             solution2 = 0)

    fun findMaxNumberWithNDigits(row: String,n: Int, maxIndex: Int): String {
        if (maxIndex > row.length ) return ""
        val maxInt =  row.subSequence(0,maxIndex).maxByOrNull { it.code }
        if (maxInt == null) {
            return ""
        }
        val newIndex = row.indexOf(maxInt)
        val newRow = row.substring(newIndex +1)
        val newMaxIndex = newRow.length -n +2
        return "$maxInt" + findMaxNumberWithNDigits(newRow, n - 1, newMaxIndex)
    }

    fun solveDay3(input: List<String>, n: Int): Long {
        return input
            .sumOf{
                val maxIndex = it.length -n +1
                findMaxNumberWithNDigits(it, n,maxIndex).toLong()
            }
    }
    fun part1(input: List<String>) = solveDay3(input,2)


    fun part2(input: List<String>) = solveDay3(input,12)

    try {
        val exampleInput1 = readInput("day_03_demo", "2025")
        val exampleInput2 = readInput("day_03_demo", "2025")
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
//            val part2DemoSolution = part2(mutableListOf("234234234234278"))
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == 3121910778619)
        }

        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_03", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1.toLong())
            if(config.submit1) handleSubmit(2025,3,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_03", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == 171297349921310)
            if(config.submit2) handleSubmit(2025,3,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}