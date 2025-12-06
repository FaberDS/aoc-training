/**
 * Advent of code (2025) solution for day 6 by Denis Schüle.
 * [Advent of code 2025-6 ](https://adventofcode.com/2025/day/6)
 **/
import aoc.handleSubmit
import extensions.printSeparated
import kotlin.time.measureTimedValue


fun main() {

     val config = ConfigForDay(
             submit1 = false,
             submit2 = false,
             check1 = false,
             check2 = false,
             checkDemo1 = false,
             checkDemo2 = false,
             execute1 = false,
             execute2 = false,
             execute1demo = true,
             execute2demo = false,
             exampleSolution1 = 0,
             exampleSolution2 = 0,
             solution1 = 0,
             solution2 = 0)

    fun part1(input: List<String>): Int {
        var result = 0
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        return result
    }

    try {
        val exampleInput1 = readInput("day_06_demo", "2025")
        val exampleInput2 = readInput("day_06_demo", "2025")
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
                val input = readInput("day_06", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1)
            if(config.submit1) handleSubmit(2025,6,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_06", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == config.solution2)
            if(config.submit2) handleSubmit(2025,6,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}