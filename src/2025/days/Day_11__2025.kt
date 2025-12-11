/**
 * Advent of code (2025) solution for day 11 by Denis Schüle.
 * https://adventofcode.com/2025/day/11
 */
import aoc.AoCDay

private typealias Commands = Map<String,List<String>>
object Day11 : AoCDay<Long>(
    year = 2025,
    day = 11,
    example1Expected = 5,
    example2Expected = 2,
    answer1Expected = 613,
    answer2Expected = 372918445876116,
) {
    private const val START_1 = "you"
    private const val START_2 = "svr"
    private const val FFT = "fft"
    private const val DAC = "dac"
    private const val END = "out"
    private const val FFT_BIT = 1
    private const val DAC_BIT = 2
    private const val BOTH = FFT_BIT or DAC_BIT
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

    fun findDataForDevice(data: List<String>, commands: Commands, i: Int, history: String, target: String): List<String>{
        if(data.isEmpty()) return emptyList()
        val paths: MutableList<String> = mutableListOf()
        val first = data.first()
        if (first.isNotEmpty() && first == target) {
            return listOf(history + first)
        }
        if (data.isEmpty()) return paths

        for(d in data) {
            val nextData = commands.getOrDefault(d, listOf())
            val nextI = i +1
            val h = history + d
            val path = findDataForDevice(nextData, commands, nextI, h,target)
            for (p in path) {
                paths.add(p)
            }
        }
        return paths
    }

    override fun part1(input: List<String>): Long {
        val commands = splitInput(input)
        val firstRow = commands.getOrDefault(START_1, listOf())
        return findDataForDevice(firstRow,commands,0,"",END).count().toLong()
    }

    override fun part2(input: List<String>): Long {
        val commands = splitInput(input)
        val history = mutableMapOf<Pair<String,Int>,Long>()
        fun findPaths(device: String,mask: Int): Long {
            var m = mask
            if (device == DAC) m = m or DAC_BIT
            if (device == FFT) m = m or FFT_BIT

            val key = device to m
            history[key]?.let { return it }

            val result: Long = when {
                device == END ->
                    if (m == BOTH) 1L else 0L

                else -> {
                    val devices = commands[device] ?: emptyList()
                    if (devices.isEmpty()) {
                        0L
                    } else {
                        devices.sumOf { next -> findPaths(next, m) }
                    }
                }
            }

            history[key] = result
            return result
        }

        return findPaths(START_2,0)
    }
}

// -------- manual runner --------

fun main() =
    Day11.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
