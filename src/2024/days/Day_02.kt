/**
 * Advent of code (2024) solution for day 2 by Denis Schüle.
 * [Advent of code 2024-2 ](https://adventofcode.com/2024/day/2)
 **/

fun main() {

    val exampleSolution = 0 
    
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    try {
    
        // --- TEST DEMO INPUT ---
        val exampleInput = readInput("day_02_demo", "2024")
        val part1_demo_solution = part1(exampleInput)
        println("Part 1 Demo: ${part1_demo_solution}") 
        // check(part1_demo_solution == exampleSolution)
        
        
        // --- RUN FULL INPUT ---
        // Reads input from the file src/2024/input/day_02.txt
        // NOTE: You need to implement or import the readInput function.
        val input = readInput("day_02", "2024")

        val part1_solution = part1(input)
        println("Part 1: ${part1_solution}") 
//        check(part1_solution == 1)

        val part2_solution = part2(input)

        println("Part 2: ${part2_solution}") 
//        check(part2_solution == 1)

    } catch (t: Throwable) {
        t.printStackTrace()
        throw t
    }
}