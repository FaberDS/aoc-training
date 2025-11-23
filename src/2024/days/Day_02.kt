import kotlin.math.abs

/**
 * Advent of code (2024) solution for day 2 by Denis Schüle.
 * [Advent of code 2024-2 ](https://adventofcode.com/2024/day/2)
 **/

fun main() {

    val exampleSolution1 = 2
    val exampleSolution2 = 4

    fun isValidDif(current: Int, next: Int): Boolean {
        val dif = abs(current - next)
        if (dif < 1 || dif > 3) return false
        return true
    }

    fun isDecreasing(current: Int, next: Int): Boolean {
        return current > next
    }

    fun isSafeReportPart1(levels: List<Int>): Boolean {
        var direction: Boolean? = null

        for (i in 0 until levels.size - 1) {
            val current = levels[i]
            val next = levels[i + 1]

            val isValidDifference = isValidDif(current, next)
            val isDecreasing = isDecreasing(current, next)

            if (!isValidDifference) return false

            if (direction == null) {
                direction = isDecreasing
            } else if (direction != isDecreasing) {
                return false
            }
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val intLines = splitInputToIntList(input)
        var safeRows = 0
        for (line in intLines) {
            if (isSafeReportPart1(line)) {
                safeRows++
            }
        }
        return safeRows
    }

    fun part2(input: List<String>): Int {
        val intLines = splitInputToIntList(input)
        var safeRows = 0

        for (line in intLines) {
            if (isSafeReportPart1(line)) {
                safeRows++
                continue
            }

            var foundSafeRemoval = false
            for (j in line.indices) {
                val tempLevels = line.toMutableList()
                tempLevels.removeAt(j)

                if (tempLevels.size >= 2 && isSafeReportPart1(tempLevels)) {
                    foundSafeRemoval = true
                    break
                }
            }

            if (foundSafeRemoval) {
                safeRows++
            }
        }
        return safeRows
    }

    try {
        // --- TEST DEMO INPUT ---
        val exampleInput = readInput("day_02_demo", "2024")
        val part1_demo_solution = part1(exampleInput)
        println("Part 1 Demo: ${part1_demo_solution}")
        check(part1_demo_solution == exampleSolution1)
        val part2_demo_solution = part2(exampleInput)
        println("Part 2 Demo: ${part2_demo_solution}")
        check(part2_demo_solution == exampleSolution2)

        // --- RUN FULL INPUT ---
        val input = readInput("day_02", "2024")

        val part1_solution = part1(input)
        println("Part 1: ${part1_solution}")
        check(part1_solution == 549)

        val part2_solution = part2(input)

        println("Part 2: ${part2_solution}")
        check(part2_solution == 589)

    } catch (t: Throwable) {
        t.printStackTrace()
        throw t
    }
}