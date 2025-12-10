import aoc.AoCPaths
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    // Example inputs and real input using AoCPaths for base names
    private val exampleInput1 = readInput(
        AoCPaths.demo1Base(10),
        "2025",
    )

    private val exampleInput2 = readInput(
        AoCPaths.demo2Base(10),
        "2025",
    )

    private val realInput = readInput(
        AoCPaths.realInputBase(10),
        "2025",
    )

    @Test
    fun example_part1() {
        assertEquals(7L, Day10.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        assertEquals(33L, Day10.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        assertEquals(385L, Day10.part1(realInput))
    }

    @Test
    fun real_part2() {
        assertEquals(16757L, Day10.part2(realInput))
    }
}
