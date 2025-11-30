/**
 * Advent of code (2023) solution for day 1 by Denis Schüle.
 * [Advent of code 2023-1 ](https://adventofcode.com/2023/day/1)
 **/
 import aoc.submitAocAnswer
 import extensions.printSeparated
 import jdk.internal.joptsimple.util.RegexMatcher.regex
 import kotlin.time.measureTimedValue


fun main() {

    val exampleSolution1 = 142
    val exampleSolution2 = 281
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

    fun part1(input: List<String>): Int {
        val regex= Regex("""(\d)""")
        return input.sumOf { extractNumbers(it, regex) }
    }

    fun part2(input: List<String>): Int {
        val regex = getAlphaNumericRegex(true)
        return input.sumOf { extractNumbers(it, regex) }
    }

    try {

        /* --- TEST DEMO INPUT --- */
        val exampleInput1 = readInput("day_01_demo", "2023")
        val exampleInput2 = readInput("day_01_02_demo", "2023")
        val part1_demo_solution = part1(exampleInput1)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}")
         check(part1_demo_solution == exampleSolution1)


        "Part 2 Demo".printSeparated()
        val part2_demo_solution = part2(exampleInput2)
        println("- Part 2 Demo: $part2_demo_solution")
        check(part2_demo_solution == exampleSolution2)

        /* --- RUN FULL INPUT --- */

        "Part 1".printSeparated()
        val (part1_solution, timeTakenPart1) = measureTimedValue {
            val input = readInput("day_01", "2023")
            part1(input)
        }
        println("- Part 1: ${part1_solution} in $timeTakenPart1")
        check(part1_solution == 54634)

//          val result = submitAocAnswer(
//                      year = 2023,
//                      day = 1,
//                      level = 1,
//                      answer = part1_solution.toString()
//                  )
//          println("ok=${result.ok}, status=${result.status}, msg=${result.message}")
//
        "Part 2".printSeparated()
        val (part2_solution, timeTakenPart2) = measureTimedValue {
            val input = readInput("day_01", "2023")
            part2(input)
        }
        println("- Part 2: ${part2_solution} in $timeTakenPart2")
        check(part2_solution == 53855)
//        val result = submitAocAnswer(
//            year = 2023,
//            day = 1,
//            level = 2,
//            answer = part2_solution.toString()
//        )
//        println("ok=${result.ok}, status=${result.status}, msg=${result.message}")


    } catch (t: Throwable) {
        t.printStackTrace()
    }
}