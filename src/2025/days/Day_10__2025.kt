/**
 * Advent of code (2025) solution for day 10 by Denis Schüle.
 * https://adventofcode.com/2025/day/10
 */
import aoc.AoCDay
import extensions.allSubsets
import utils.Machine
import utils.minPressesForMachine
import kotlin.text.split

private fun parseButton(line: String): List<List<Int>> =
    Regex("""\(([^)]*)\)""").findAll(line)
        .map { match ->
            val inner = match.groupValues[1].trim()
            if (inner.isEmpty()) {
                emptyList()
            } else {
                inner.split(',')
                    .map { it.trim().toInt() }
            }
        }.toList()

fun parseMachine(line: String): Machine {
    val targetMatch = Regex("""\{([^}]*)\}""").find(line)
        ?: error("No targets found in line: $line")

    val targets = targetMatch.groupValues[1]
        .split(',')
        .filter { it.isNotBlank() }
        .map { it.trim().toInt() }
        .toIntArray()

    val buttons = parseButton(line)

    return Machine(
        targets = targets,
        buttons = buttons
    )
}

fun calculateButtonTaps(targetIndicatorLight: List<Boolean>, buttons: List<List<Int>>): Long {

    var minTaps = Long.MAX_VALUE
    val buttonCombinations = buttons.allSubsets()

    for (buttonSet in buttonCombinations) {
        val indicatorLight = MutableList(targetIndicatorLight.size) { false }
        var taps = 0L

        for (bs in buttonSet) {
            taps++
            if (taps >= minTaps) break

            for (b in bs) {
                indicatorLight[b] = !indicatorLight[b]
            }
        }

        if (taps >= minTaps) continue
        if (indicatorLight == targetIndicatorLight && taps < minTaps) {
            minTaps = taps
        }
    }

    return minTaps
}
object Day10 : AoCDay<Long>(
    year = 2025,
    day = 10,
    example1Expected = 7,
    example2Expected = 33,
    answer1Expected = 385, // 91,165,385
    answer2Expected = 16757,
) {
    val regexP = Regex("""[\(\)]""")
    val regex = Regex("""(?:\[([\.#]*)\]) ((?:\(\d(?:\,\d)*\) )*)""")


    override fun part1(input: List<String>): Long {
        var result = 0L

        for ((i,row) in input.withIndex()) {
            val match = regex.findAll(row)
            var indcatorLight = listOf<Boolean>()
            var buttons = listOf<List<Int>>()
            for (match in match) {
                val indicatorLightData = match.groupValues[1]
                indcatorLight = indicatorLightData.toCharArray().map { it == '#' }.toMutableList()

                val buttonData = match.groupValues[2]
                val btWithouthParantheses = buttonData.replace(regexP,"")
                val bt = btWithouthParantheses.split(" ").dropLast(1)
                buttons = bt.map{ it.filter{ it.isDigit()}.map (Character::getNumericValue).toList()}.toList()
            }
            val taps =  calculateButtonTaps(indcatorLight, buttons)
            result += taps
        }
        return result
    }

    override fun part2(input: List<String>): Long {
        val result = input
            .filter { it.isNotBlank() }
            .map(::parseMachine)
            .sumOf(::minPressesForMachine).toLong()
        println("Part 2: $result")
        return result
    }
}

// -------- manual runner --------

fun main() =
    Day10.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
