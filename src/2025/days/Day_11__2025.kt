/**
 * Advent of code (2025) solution for day 11 by Denis Schüle.
 * https://adventofcode.com/2025/day/11
 */
import aoc.AoCDay

private typealias Commands = Map<String,List<String>>

object Day11 : AoCDay<Long>(
    year = 2025,
    day = 11,
    // TODO: fill these once you know them
    example1Expected = 5,
    example2Expected = null,
    answer1Expected = 613,
    answer2Expected = null,
) {
    fun printCommands(commands: Commands) {
        commands.forEach {
            println("${it.key}: ${it.value}")
        }
    }
    fun splitInput(input: List<String>): Commands {
        val commands: Commands = input.associate { line ->
            val rowLine = line.split(':')
            val key = rowLine.first().trim()

            val args = rowLine.drop(1)
                .flatMap { it.trim().split(" ") }
                .filter { it.isNotEmpty() }
                .toList()
            key to args
        }.toMutableMap()
        return commands
    }

    fun findDataForDevice(data: List<String>, commands: Commands, i: Int, history: String): List<String>{
//        println("${i.toString().padStart(4,'0')}")

        val paths: MutableList<String> = mutableListOf()
        val first = data.first()
        if (first.isNotEmpty() && first == "out") {
            return listOf(first)
        }
        if (data.isEmpty()) return paths

        for(d in data) {
            val nextData = commands.getOrDefault(d, listOf())
            val nextI = i +1
            val h = history + d
            val path = findDataForDevice(nextData, commands, nextI, h)
//            println("       -> ${nextI.toString().padStart(5)} d: $d | p.count: ${path.count()} | $path | history: $h")
            for (p in path) {
                paths.add(p)
            }
        }
//        println("   -> ${i.toString().padStart(4)} | paths: ${paths.count()} | $paths")
        return paths
    }
    override fun part1(input: List<String>): Long {
        var result = 0L
        val commands = splitInput(input)
        printCommands(commands)
        val firstRow = commands.getOrDefault("you", listOf())
        val paths = findDataForDevice(firstRow,commands,0,"")
        result = paths.count().toLong()
        return result
    }

    override fun part2(input: List<String>): Long {
        var result = 0L
        // TODO
        return result
    }
}

// -------- manual runner --------

fun main() =
    Day11.run(
        runExamples = true,
        runReal = false,
        runPart1 = false,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
