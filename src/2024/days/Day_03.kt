/**
 * Advent of code (2024) solution for day 3 by Denis Schüle.
 * [Advent of code 2024-3 ](https://adventofcode.com/2024/day/3)
 **/
 import extensions.printSeparated
 import jdk.internal.joptsimple.util.RegexMatcher.regex


fun main() {

    val exampleSolution1 = 161
    val exampleSolution2 = 48
    
    fun part1(input: List<String>): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        var sum = 0
        for (line in input) {
            val match = regex.findAll(line)
                match.forEach {
                    val (x, y) = it.destructured
                    sum += x.toInt() * y.toInt()
                    println("$x $y $sum")
                }

        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")
        var multiply = true
        var sum = 0
        for (line in input) {
            val match = regex.findAll(line)

            match.forEach {
                println(it.value)
                val value = it.value
                if (value.startsWith("mul") && multiply){
                    val (x, y) = it.destructured
                    sum += x.toInt() * y.toInt()
                    println("$x $y $sum")
                } else if (value.startsWith("don't")){
                    multiply = false
                }else if (value.startsWith("do")){
                    multiply = true
                }

            }

        }
        return sum
    }

    try {
    
        // --- TEST DEMO INPUT ---
        val exampleInput = readInput("day_03_demo", "2024")
        val part1_demo_solution = part1(exampleInput)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}") 
         check(part1_demo_solution == exampleSolution1)

        "Part 2 Demo".printSeparated()
        val exampleInput2 = readInput("day_03_demo_2", "2024")
        val part2_demo_solution = part2(exampleInput2)

        println("- Part 2 Demo: ${part2_demo_solution}")
         check(part2_demo_solution == exampleSolution2)
        
//         --- RUN FULL INPUT ---
//         Reads input from the file src/2024/input/day_03.txt
//         NOTE: You need to implement or import the readInput function.
        val input = readInput("day_03", "2024")

        val part1_solution = part1(input)
        "Part 1".printSeparated()
        println("- Part 1: ${part1_solution}")
        check(part1_solution == 155955228)

        val part2_solution = part2(input)
        "Part 2".printSeparated()
        println("- Part 2: ${part2_solution}")
        check(part2_solution == 100189366)

    } catch (t: Throwable) {
        t.println()
        t.printStackTrace()
        throw t
    }
}