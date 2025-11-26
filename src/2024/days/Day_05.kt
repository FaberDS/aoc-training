/**
 * Advent of code (2024) solution for day 5 by Denis Schüle.
 * [Advent of code 2024-5 ](https://adventofcode.com/2024/day/5)
 **/
 import extensions.middleValue
 import extensions.printSeparated
 import jdk.jfr.internal.consumer.EventLog.update
 import java.util.Collections
 import kotlin.time.measureTimedValue


fun main() {

    val exampleSolution1 = 143
    val exampleSolution1RowCount = 3
    val exampleSolution2 = 123

    fun splitInput(input: List<String>): Pair<List<Pair<Int,Int>>,List<List<Int>>> {
        val orderList = mutableListOf<Pair<Int, Int>>()
        val updateList = mutableListOf<List<Int>>()
        for (line in input) {
            if (line.contains('|')) {
                val splitted = line.split('|')
                                        .map { it.toInt() }
                orderList.add(Pair(splitted.first(), splitted.last()))
            }
            if (line.contains(',')) {
                updateList.add(line.split(',').map { it.toInt() }.toList())
            }
        }
        return Pair(orderList,updateList)
    }

    fun filterUpdateLists(orderRules: List<Pair<Int,Int>>,updateList:List<List<Int>>, filterValid: Boolean): List<List<Int>> {
        val filteredUpdate = mutableListOf<List<Int>>()
        for (update in updateList) {
            val pageToIndex = update.withIndex().associate { it.value to it.index }

            var orderIsValid = true

            for ((predecessor, successor) in orderRules) {
                val indexPre = pageToIndex[predecessor]
                val indexSucc = pageToIndex[successor]

                if (indexPre != null && indexSucc != null) {
                    if (indexPre >= indexSucc) {
                        orderIsValid = false
                        break
                    }
                }
            }
            if (orderIsValid == filterValid) {
                filteredUpdate.add(update)
            }
        }
        return filteredUpdate
    }
    fun part1(input: List<String>): Int {
        val (orderRules, updateList) = splitInput(input)
        return filterUpdateLists(orderRules, updateList, true)
            .sumOf { it.middleValue() }
    }

    fun part2(input: List<String>): Int {
        val (orderRules, updateList) = splitInput(input)
        val invalidUpdates = filterUpdateLists(orderRules, updateList, false)
        println("orderRules: $orderRules")
        for(row in invalidUpdates) {
            val pageToIndex = row.withIndex().associate { it.value to it.index }
            println("-----------------\nrow: $row")
            for((predecessor, successor) in orderRules) {
                val indexPre = pageToIndex[predecessor]
                val indexSucc = pageToIndex[successor]

                if (indexPre != null && indexSucc != null) {
                    if (indexPre > indexSucc) {
                        println("pre row: $row")
                        Collections.swap(row,indexPre,indexSucc)
                        println("post row: $row")
                    }
                }
            }
        }
        return invalidUpdates.sumOf { it.middleValue() }
    }

    try {

        /* --- TEST DEMO INPUT --- */
        val exampleInput = readInput("day_05_demo", "2024")
//        val part1_demo_solution = part1(exampleInput)
//        "Part 1 Demo".printSeparated()
//        println("- Part 1 Demo: $part1_demo_solution")
//        check(part1_demo_solution == exampleSolution1)
//

        "Part 2 Demo".printSeparated()
        val part2_demo_solution = part2(exampleInput)
        println("- Part 2 Demo: $part2_demo_solution")
//        check(part2_demo_solution == exampleSolution2)

        /* --- RUN FULL INPUT --- */

//        "Part 1".printSeparated()
//        val (part1_solution, timeTakenPart1) = measureTimedValue {
//            val input = readInput("day_05", "2024")
//            part1(input)
//        }
//        println("- Part 1: ${part1_solution} in $timeTakenPart1")
//        check(part1_solution == 4996)

        "Part 2".printSeparated()
        val (part2_solution, timeTakenPart2) = measureTimedValue {
            val input = readInput("day_05", "2024")
            part2(input)
        }
        println("- Part 2: ${part2_solution} in $timeTakenPart2")
//        check(part2_solution == 6311)

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}