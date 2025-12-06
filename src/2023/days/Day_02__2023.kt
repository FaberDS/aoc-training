/**
 * Advent of code (2023) solution for day 2 by Denis Schüle.
 * https://adventofcode.com/2023/day/2
 */
import aoc.AoCDay
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

object Day02 : AoCDay<Int>(
    year = 2023,
    day = 2,
    // fill these once you know them:
    example1Expected = null,
    example2Expected = null,
    answer1Expected = null,
    answer2Expected = null,
) {

    override fun part1(input: List<String>): Int {
        // TODO
        return 0
    }

    override fun part2(input: List<String>): Int {
        // TODO
        return 0
    }
}

// -------- manual runner --------

fun main() =
    Day02.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )

// -------- tests (4 “standard” ones) --------

class Day02Test {

    // adjust paths if your demo filenames differ
    private val exampleInput1 = readInput("day_02_demo", "2023")
    private val exampleInput2 = readInput("day_02_02_demo", "2023")
    private val realInput     = readInput("day_02", "2023")

    @Test
    fun example_part1() {
        // replace ??? when you know the expected value
        assertEquals(??? , Day02.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        assertEquals(??? , Day02.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        // once you solved it the first time, paste the answer here
        assertEquals(??? , Day02.part1(realInput))
    }

    @Test
    fun real_part2() {
        assertEquals(??? , Day02.part2(realInput))
    }
}