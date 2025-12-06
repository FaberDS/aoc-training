package aoc

import java.io.File

object AoCPaths {
    private const val SRC_ROOT = "src"
    private const val TEST_ROOT = "src/test/kotlin"

    fun dayPadded(day: Int): String = "%02d".format(day)

    // --- directories ---

    fun inputDir(year: Int) = File("$SRC_ROOT/$year/input")
    fun daysDir(year: Int)  = File("$SRC_ROOT/$year/days")
    fun testsDir(year: Int) = File("$TEST_ROOT/$year")

    // --- base names (no .txt) used by readInput ---

    fun realInputBase(day: Int): String =
        "day_${dayPadded(day)}"

    fun demo1Base(day: Int): String =
        realInputBase(day) + "_demo"

    fun demo2Base(day: Int): String =
        realInputBase(day) + "_2_demo"   // <-- we standardize on this

    // --- actual files (with .txt) ---

    fun realInputFile(year: Int, day: Int): File =
        File(inputDir(year), "${realInputBase(day)}.txt")

    fun demo1File(year: Int, day: Int): File =
        File(inputDir(year), "${demo1Base(day)}.txt")

    fun demo2File(year: Int, day: Int): File =
        File(inputDir(year), "${demo2Base(day)}.txt")

    // --- solution & test files ---

    fun solutionFile(year: Int, day: Int): File =
        File(daysDir(year), "Day_${dayPadded(day)}__${year}.kt")

    fun testFile(year: Int, day: Int): File =
        File(testsDir(year), "Day_${dayPadded(day)}__${year}_Test.kt")

    // --- AoC URL ---

    fun aocUrl(year: Int, day: Int): String =
        "https://adventofcode.com/$year/day/$day"
}
