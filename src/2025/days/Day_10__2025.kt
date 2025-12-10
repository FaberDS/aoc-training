/**
 * Advent of code (2025) solution for day 10 by Denis Schüle.
 * https://adventofcode.com/2025/day/10
 */
import aoc.AoCDay
import extensions.allSubsets
import extensions.diffIndices
import extensions.flip
import kotlin.text.split

/**
 *   PART-1
 *
 *   PART-2
 *   // TODO
 *
 */


fun calculateButtonTaps(targetIndicatorLight: List<Boolean>, buttons: List<List<Int>>): Long {
    var taps = 0L

    var minValue = Long.MAX_VALUE
    val buttonCombinations = buttons.allSubsets()

    for( buttonSet in buttonCombinations) {
        val indicatorLight = MutableList<Boolean>(targetIndicatorLight.count()){false}
        taps = 0L
        for(bs in buttonSet) {
            taps ++
            for(b in bs) {
                indicatorLight.flip(b)
            }
        }
        val c = diffIndices(targetIndicatorLight, indicatorLight)
        val match = c.isEmpty()
        if(match) {
            if (taps < minValue){
                minValue = taps
            }
        }
    }
    return minValue
}
object Day10 : AoCDay<Long>(
    year = 2025,
    day = 10,
    // TODO: fill these once you know them
    example1Expected = 7,
    example2Expected = null,
    answer1Expected = 385, // 91,165,385
    answer2Expected = null,
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
        var result = 0L
        // TODO
        return result
    }
}

// -------- manual runner --------

fun main() =
    Day10.run(
        runExamples = true,
        runReal = false,
        runPart1 = false,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
