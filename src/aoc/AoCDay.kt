package aoc

import extensions.printSeparated
import utils.readInput
import kotlin.time.measureTimedValue

abstract class AoCDay<T>(
    val year: Int,
    val day: Int,
    val example1Expected: T? = null,
    val example2Expected: T? = null,
    val answer1Expected: T? = null,
    val answer2Expected: T? = null,
) {
    private val dayPadded: String = day.toString().padStart(2, '0')

    abstract fun part1(input: List<String>): T
    abstract fun part2(input: List<String>): T

    fun run(
        runExamples: Boolean = true,
        runReal: Boolean = true,
        runPart1: Boolean = true,
        runPart2: Boolean = true,
        submit1: Boolean = false,
        submit2: Boolean = false,
    ) {
        try {
            if (runExamples) {
                val exampleInput1 = readInput("day_${dayPadded}_demo", "$year")
                val exampleInput2 = readInput("day_${dayPadded}_02_demo", "$year")

                if (runPart1) {
                    "Part 1 Demo".printSeparated()
                    val result = part1(exampleInput1)
                    println("- Part 1 Demo: $result")
                    example1Expected?.let { check(result == it) }
                }

                if (runPart2) {
                    "Part 2 Demo".printSeparated()
                    val result = part2(exampleInput2)
                    println("- Part 2 Demo: $result")
                    example2Expected?.let { check(result == it) }
                }
            }

            if (runReal) {
                val fullInput = readInput("day_${dayPadded}", "$year")

                if (runPart1) {
                    "Part 1".printSeparated()
                    val (result, timeTaken) = measureTimedValue {
                        part1(fullInput)
                    }
                    println("- Part 1: $result in $timeTaken")
                    answer1Expected?.let { check(result == it) }
                    if (submit1) handleSubmit(year, day, 1, result.toString())
                }

                if (runPart2) {
                    "Part 2".printSeparated()
                    val (result, timeTaken) = measureTimedValue {
                        part2(fullInput)
                    }
                    println("- Part 2: $result in $timeTaken")
                    answer2Expected?.let { check(result == it) }
                    if (submit2) handleSubmit(year, day, 2, result.toString())
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}
