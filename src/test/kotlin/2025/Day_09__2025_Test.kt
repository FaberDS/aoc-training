import aoc.AoCPaths
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    // Example inputs and real input using AoCPaths for base names
    private val exampleInput1 = readInput(
        AoCPaths.demo1Base(9),
        "2025",
    )

    private val exampleInput2 = readInput(
        AoCPaths.demo2Base(9),
        "2025",
    )

    private val realInput = readInput(
        AoCPaths.realInputBase(9),
        "2025",
    )

    @Test
    fun example_part1() {
        assertEquals(50L, Day09.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        assertEquals(24L, Day09.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        assertEquals(4755278336L, Day09.part1(realInput))
    }

    @Test
    fun real_part2() {
        assertEquals(1534043700L, Day09.part2(realInput))
    }
}
