/**
 * Advent of code (2025) solution for day 9 by Denis Schüle.
 * https://adventofcode.com/2025/day/9
 */
import aoc.AoCDay
import datastructures.getRectangleAreaTo
import datastructures.toPoints

/**
 *
 * https://arxiv.org/pdf/1708.02758
 *   PART-1
 *   // TODO
 *
 *   PART-2
 *   // TODO
 *
 */

object Day09 : AoCDay<Long>(
    year = 2025,
    day = 9,
    // TODO: fill these once you know them
    example1Expected = 50,
    example2Expected = null,
    answer1Expected = 4755278336,
    answer2Expected = null,
) {

    override fun part1(input: List<String>): Long {
        var bestArea = 0L
        val pts = input.toPoints()

        for (pt in pts) {
            for (pt2 in pts.drop(1)) {
                val area = pt.getRectangleAreaTo(pt2)
                if (area > bestArea) {
                    bestArea = area
                }
            }
        }
        return bestArea
    }

    override fun part2(input: List<String>): Long {
        var result = 0L
        // TODO
        return result
    }
}

// -------- manual runner --------

fun main() =
    Day09.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = false,
        submit1 = false,
        submit2 = false,
    )
