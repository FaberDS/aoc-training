import aoc.AoCPaths
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    // Example inputs and real input using AoCPaths for base names
    private val exampleInput1 = readInput(
        AoCPaths.demo1Base(7),
        "2025",
    )

    private val exampleInput2 = readInput(
        AoCPaths.demo2Base(7),
        "2025",
    )

    private val realInput = readInput(
        AoCPaths.realInputBase(7),
        "2025",
    )

    @Test
    fun example_part1() {
        assertEquals(21L, Day07.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        // TODO: adjust expected value once known
        assertEquals(0L, Day07.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        assertEquals(1581L, Day07.part1(realInput))
    }

    @Test
    fun real_part2() {
        // TODO: adjust expected value once known
        assertEquals(0L, Day07.part2(realInput))
    }
}
