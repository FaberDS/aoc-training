/**
 * Advent of code (2023) solution for day 1 by Denis Schüle.
 * [Advent of code 2023-1 ](https://adventofcode.com/2023/day/1)
 **/
 import Setup.ConfigForDay
 import aoc.handleSubmit
 import datastructures.getAlphabeticNumber
 import extensions.printSeparated
 import utils.getAlphaNumericRegex
 import utils.readInput
 import kotlin.time.measureTimedValue


fun main() {
    val config = ConfigForDay(
        submit1 = false,
        submit2 = false,
        check1 = true,
        check2 = true,
        checkDemo1 = false,
        checkDemo2 = false,
        execute1 = true,
        execute2 = true,
        execute1demo = true,
        execute2demo = true,
        exampleSolution1 = "142",
        exampleSolution2 = "281",
        solution1 = "54634",
        solution2 = "53855"
    )

    fun extractNumbers(line: String, regex: Regex): Int {
        val digits = regex.findAll(line)
            .map { match ->
                val value = match.groupValues[1]
                val enumValue = getAlphabeticNumber(value)
                    ?: error("Could not parse '$value' as a number")

                enumValue.toInt()
            }
            .toList()
        return digits.first() * 10 + digits.last()
    }

    fun part1(input: List<String>): String {
        val regex= Regex("""(\d)""")
        return input.sumOf { extractNumbers(it, regex) }.toString()
    }

    fun part2(input: List<String>): String {
        val regex = getAlphaNumericRegex(true)
        return input.sumOf { extractNumbers(it, regex) }.toString()
    }

    try {

        /* --- TEST DEMO INPUT --- */
        val exampleInput1 = readInput("day_01_demo", "2023")
        val exampleInput2 = readInput("day_01_02_demo", "2023")
        val part1_demo_solution = part1(exampleInput1)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}")
        if(config.checkDemo1) check(part1_demo_solution == config.exampleSolution1)


        "Part 2 Demo".printSeparated()
        val part2_demo_solution = part2(exampleInput2)
        println("- Part 2 Demo: $part2_demo_solution")
        if(config.checkDemo2) check(part2_demo_solution == config.exampleSolution2)

        /* --- RUN REAL INPUT --- */

        "Part 1".printSeparated()
        val (part1_solution, timeTakenPart1) = measureTimedValue {
            val input = readInput("day_01", "2023")
            part1(input)
        }
        println("- Part 1: ${part1_solution} in $timeTakenPart1")
        if(config.check1) check(part1_solution == config.solution1)
        if(config.submit1) handleSubmit(2023,1,1,part1_solution.toString())

        "Part 2".printSeparated()
        val (part2_solution, timeTakenPart2) = measureTimedValue {
            val input = readInput("day_01", "2023")
            part2(input)
        }
        println("- Part 2: ${part2_solution} in $timeTakenPart2")
        if(config.check2) check(part2_solution == config.solution2)
        if(config.submit2) handleSubmit(2023,1,2,part2_solution.toString())

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}