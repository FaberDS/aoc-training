/**
 * Advent of code (2023) solution for day 2 by Denis Schüle.
 * https://adventofcode.com/2023/day/2
 */
import aoc.AoCDay
import enums.AoCColor
import kotlin.collections.mutableMapOf

/**
 *   PART-1
 *   You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
 *
 * For example, the record of a few games might look like this:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
 *
 * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
 *
 * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
 *
 * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 *
 *   PART-2
 *   // TODO
 *
 */

object Day02 : AoCDay<Long>(
    year = 2023,
    day = 2,
    // TODO: fill these once you know them
    example1Expected = 8,
    example2Expected = null,
    answer1Expected = 2406,
    answer2Expected = null,
) {

    override fun part1(input: List<String>) = splitInput(input).filter { it.value }.map{it.key }.sumOf { it }.toLong()

    override fun part2(input: List<String>): Long {
        var result = 0L
        // TODO
        return result
    }

    fun isAmountValid(amount:Long,color: AoCColor): Boolean = when (color) {
        AoCColor.RED -> amount <= 12
        AoCColor.GREEN -> amount <= 13
        AoCColor.BLUE -> amount <= 14
    }
    fun splitInput(rows: List<String>): MutableMap<Int, Boolean> {
        val games = mutableMapOf<Int, Boolean>()
        for (row in rows) {
            val gameAndCubes = row.split(":")
            val cubesString = gameAndCubes.last()
            val gamesIDTemp = gameAndCubes.first().trim().split(" ").get(1).toInt()
            val splitRegex = Regex("""[,;]""")
            val cubeParts = cubesString.split(splitRegex)

            var isGameValid = true
            for(cube in cubeParts) {
                val cubeSplit = cube.trim().split(" ")
                val amount = cubeSplit.first().toLong()
                val cubeColor = AoCColor.fromString(cubeSplit[1])
                val isValid = isAmountValid(amount,cubeColor)
                if (!isValid) {
                    isGameValid = false
                    break
                }
            }
            games.set(gamesIDTemp,isGameValid)
//            println("-> gamesID: ${gamesIDTemp} isValid: $isGameValid | cubeMap: $cubeMap   | $cubesString")
        }
        return games
    }

}

// -------- manual runner --------

fun main() =
    Day02.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = false,
        submit1 = false,
        submit2 = false,
    )
