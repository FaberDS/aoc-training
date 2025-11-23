import kotlin.math.abs

/**
 * Advent of code (2024) solution for day 01 by Denis Schüle.
 * [Advent of code 2024-01](https://adventofcode.com/2024/day/2)
 **/


fun main() {
    fun part1(input: List<String>): Int {
        val splittedInput = splitInput(input)
        val list1: MutableList<Int> = splittedInput.first as MutableList<Int>;
        val list2: MutableList<Int> = splittedInput.second as MutableList<Int>;
        list1.sort()
        list2.sort()
        var distance = 0
        for (i in list1.indices){
            val left = list1.get(i)
            val right = list2.get(i)
            distance += abs(left - right)
        }
        return distance
    }

    fun part2(input: List<String>): Int {
        val splittedInput = splitInput(input)
        val list1: MutableList<Int> = splittedInput.first as MutableList<Int>;
        val list2: MutableList<Int> = splittedInput.second as MutableList<Int>;
        var distance = 0
        for (i in list1.indices){
            val factor = list1.get(i)
            val count = list2.count { it == factor }
            distance += factor * count
        }
        return distance
    }
    try {

        val input = readInput("day_01","2024")

        val part1_solution = part1(input)
        println("Part 1: $part1_solution")
        check(part1_solution == 1151792)

        val part2_solution = part2(input)
        println("Part 2: $part2_solution")
    } catch (t: Throwable) {
        t.printStackTrace()
        throw t
    }


}
