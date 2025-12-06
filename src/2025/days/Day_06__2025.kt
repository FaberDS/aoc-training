/**
 * Advent of code (2025) solution for day 6 by Denis Schüle.
 * [Advent of code 2025-6 ](https://adventofcode.com/2025/day/6)
 **/
import aoc.handleSubmit
import datastructures.ConfigForDay
import datastructures.MathOperation
import datastructures.MathOperations
import extensions.printSeparated
import extensions.toDebugString
import utils.readInput
import utils.rotate90
import utils.splitIntoStringGrid
import kotlin.collections.toMutableList
import kotlin.time.measureTimedValue

/**
 * PART-1
 * Cephalopod math doesn't look that different from normal math. The math worksheet (your puzzle input) consists of a list of problems; each problem has a group of numbers that need to be either added (+) or multiplied (*) together.
 *
 * However, the problems are arranged a little strangely; they seem to be presented next to each other in a very long horizontal list. For example:
 *
 * 123 328  51 64
 *  45 64  387 23
 *   6 98  215 314
 * *   +   *   +
 * Each problem's numbers are arranged vertically; at the bottom of the problem is the symbol for the operation that needs to be performed. Problems are separated by a full column of only spaces. The left/right alignment of numbers within each problem can be ignored.
 *
 * So, this worksheet contains four problems:
 *
 * 123 * 45 * 6 = 33210
 * 328 + 64 + 98 = 490
 * 51 * 387 * 215 = 4243455
 * 64 + 23 + 314 = 401
 * To check their work, cephalopod students are given the grand total of adding together all of the answers to the individual problems. In this worksheet, the grand total is 33210 + 490 + 4243455 + 401 = 4277556.
 *
 * Of course, the actual worksheet is much wider. You'll need to make sure to unroll it completely so that you can read the problems clearly.
 *
 * Solve the problems on the math worksheet. What is the grand total found by adding together all of the answers to the individual problems?
 *
 *
 * PART-2
 * 123 328  51 64
 *  45 64  387 23
 *   6 98  215 314
 * *   +   *   +
 * Reading the problems right-to-left one column at a time, the problems are now quite different:
 *
 * The rightmost problem is 4 + 431 + 623 = 1058
 * The second problem from the right is 175 * 581 * 32 = 3253600
 * The third problem from the right is 8 + 248 + 369 = 625
 * Finally, the leftmost problem is 356 * 24 * 1 = 8544
 * Now, the grand total is 1058 + 3253600 + 625 + 8544 = 3263827.
 */

fun main() {

     val config = ConfigForDay(
         submit1 = false,
         submit2 = false,
         check1 = true,
         check2 = true,
         checkDemo1 = true,
         checkDemo2 = true,
         execute1 = true,
         execute2 = true,
         execute1demo = true,
         execute2demo = true,
         exampleSolution1 = 4277556,
         exampleSolution2 = 3263827,
         solution1 = 0,
         solution2 = 0
     )

    fun handleOperationPart1(numbers: MutableList<String>): Long{
        val operator: MathOperation = MathOperation.fromChar(numbers.removeFirst() .first())!!
        return numbers.map{it.toLong()}.reduce { acc,next -> operator.performCalculation(acc,next) }
    }

    fun part1(input: List<String>): Long {
        return rotate90(splitIntoStringGrid(input)).map{it.toMutableList()}.sumOf { handleOperationPart1(it) }
    }
    
    fun extractMathOperationDetails(row: String): Set<MathOperations> {
        var currentOperator = MathOperation.fromChar(row[0])!!
        var startIndex = 0
        val operations = mutableSetOf<MathOperations>()
        for((i,operator) in row.withIndex()){
            if(i==0) continue
            if(operator.isWhitespace()) continue
            val nextOperator = MathOperation.fromChar(operator)
            if(nextOperator != null) {
                val end = i - 2
                operations.add(MathOperations(startIndex, end, currentOperator))
                currentOperator = nextOperator
                startIndex = i
            }
        }
        // complete last operation
        val end = row.length + 1
        operations.add(MathOperations(startIndex, end, currentOperator))
        return operations
    }

    fun part2(input: List<String>): Long {
        val operations = extractMathOperationDetails(input.last())
        val grid: List<CharArray> = input.dropLast(1).map { row ->
            row.toCharArray()
        }
        val result =  operations.sumOf { op ->
            val digits = mutableListOf<Long>()
            for(x in op.from..op.to){
                var digitString = ""
                for(rows in grid) {
                    val value = rows.get(x)
                    if(value.isDigit()) {
                        digitString += value.toString()
                    }
                }
                digits.add(digitString.toLong())
            }
            digits.reduce{acc, next -> op.operation.performCalculation(acc.toLong(),next.toLong())}
        }
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
            if(config.checkDemo1) check(part1DemoSolution == config.exampleSolution1.toLong())
        }

        if(config.execute2demo) {
            "Part 2 Demo".printSeparated()
            val part2DemoSolution = part2(exampleInput2 as MutableList<String>)
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == config.exampleSolution2.toLong())
        }

        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_06", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == 4076006202939)
            if(config.submit1) handleSubmit(2025,6,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_06", "2025")
                part2(input as MutableList<String>)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if(config.check2) check(part2Solution == 7903168391557)
            if(config.submit2) handleSubmit(2025,6,2,part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}