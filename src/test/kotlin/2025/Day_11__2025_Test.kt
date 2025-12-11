import aoc.AoCPaths
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    // Example inputs and real input using AoCPaths for base names
    private val exampleInput1 = readInput(
        AoCPaths.demo1Base(11),
        "2025",
    )

    private val exampleInput2 = readInput(
        AoCPaths.demo2Base(11),
        "2025",
    )

    private val realInput = readInput(
        AoCPaths.realInputBase(11),
        "2025",
    )

    @Test
    fun example_part1() {
        assertEquals(5L, Day11.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        assertEquals(2L, Day11.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        assertEquals(613L, Day11.part1(realInput))
    }

    @Test
    fun real_part2() {
        assertEquals(372918445876116L, Day11.part2(realInput))
    }
}
