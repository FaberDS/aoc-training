/**
 * Advent of code (2023) solution for day 1 by Denis Schüle.
 * [Advent of code 2023-1 ](https://adventofcode.com/2023/day/1)
 **/
 import aoc.submitAocAnswer
 import extensions.printSeparated


fun main() {

    val exampleSolution1 = 0
    val exampleSolution2 = 0

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    try {

        /* --- TEST DEMO INPUT --- */
        val exampleInput = readInput("day_01_demo", "2023")
        val part1_demo_solution = part1(exampleInput)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}")
        // check(part1_demo_solution == exampleSolution1)


//        "Part 2 Demo".printSeparated()
//        val part2_demo_solution = part2(exampleInput)
//        println("- Part 2 Demo: $part2_demo_solution")
//        check(part2_demo_solution == exampleSolution2)

        /* --- RUN FULL INPUT --- */

//        "Part 1".printSeparated()
//        val (part1_solution, timeTakenPart1) = measureTimedValue {
//            val input = readInput("day_01", "2023")
//            part1(input)
//        }
//        println("- Part 1: ${part1_solution} in $timeTakenPart1")
//        check(part1_solution == 1)

//          val result = submitAocAnswer(
//                      year = 2023,
//                      day = 1,
//                      level = 1,
//                      answer = part1_solution.toString()
//                  )
//          println("ok=${result.ok}, status=${result.status}, msg=${result.message}")

//        "Part 2".printSeparated()
//        val (part2_solution, timeTakenPart2) = measureTimedValue {
//            val input = readInput("day_01", "2023")
//            part2(input)
//        }
//        println("- Part 2: ${part2_solution} in $timeTakenPart2")
//        check(part2_solution == 1)

    } catch (t: Throwable) {
        t.printStackTrace()
        throw t
    }
}