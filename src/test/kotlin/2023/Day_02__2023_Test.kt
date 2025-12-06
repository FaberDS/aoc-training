import aoc.AoCPaths
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    // Example inputs and real input using AoCPaths for base names
    private val exampleInput1 = readInput(
        AoCPaths.demo1Base(2),
        "2023",
    )

    private val exampleInput2 = readInput(
        AoCPaths.demo2Base(2),
        "2023",
    )

    private val realInput = readInput(
        AoCPaths.realInputBase(2),
        "2023",
    )

    @Test
    fun example_part1() {
        // TODO: adjust expected value once known
        assertEquals(8L, Day02.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        // TODO: adjust expected value once known
        assertEquals(0L, Day02.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        // TODO: adjust expected value once known
        assertEquals(2406L, Day02.part1(realInput))
    }

    @Test
    fun real_part2() {
        // TODO: adjust expected value once known
        assertEquals(0L, Day02.part2(realInput))
    }
}
