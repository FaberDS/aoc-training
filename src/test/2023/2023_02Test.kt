import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    // adjust paths if your demo filenames differ
    private val exampleInput1 = readInput("day_02", "2023")
    private val exampleInput2 = readInput("day_02", "2023")
    private val realInput     = readInput("day_02", "2023")

    @Test
    fun example_part1() {
        // TODO
        assertEquals(0L , Day02.part1(exampleInput1))
    }

    @Test
    fun example_part2() {
        // TODO
        assertEquals(0L , Day02.part2(exampleInput2))
    }

    @Test
    fun real_part1() {
        // TODO
        assertEquals(0L , Day02.part1(realInput))
    }

    @Test
    fun real_part2() {
        // TODO
        assertEquals(0L , Day02.part2(realInput))
    }
}