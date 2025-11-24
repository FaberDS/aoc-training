/**
 * Advent of code (2024) solution for day 3 by Denis Schüle.
 * [Advent of code 2024-3 ](https://adventofcode.com/2024/day/3)
 **/
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
    
        // --- TEST DEMO INPUT ---
        val exampleInput = readInput("day_03_demo", "2024")
        val part1_demo_solution = part1(exampleInput)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}") 
        // check(part1_demo_solution == exampleSolution1)
        val part2_demo_solution = part2(exampleInput)
        "Part 2 Demo".printSeparated()
        println("- Part 2 Demo: ${part2_demo_solution}") 
        // check(part2_demo_solution == exampleSolution2)
        
        // --- RUN FULL INPUT ---
        // Reads input from the file src/2024/input/day_03.txt
        // NOTE: You need to implement or import the readInput function.
        val input = readInput("day_03", "2024")

        val part1_solution = part1(input)
        "Part 1".printSeparated()
        println("- Part 1: ${part1_solution}") 
//        check(part1_solution == 1)

        val part2_solution = part2(input)
        "Part 2".printSeparated()
        println("- Part 2: ${part2_solution}") 
//        check(part2_solution == 1)

    } catch (t: Throwable) {
        t.printStackTrace()
        throw t
    }
}