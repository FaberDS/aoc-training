import RotationDirection
import com.sun.tools.javac.jvm.Gen.one

/**
 * Basic Coordinate type
 */
data class Coord(val row: Int, val col: Int)

enum class NumbersAlphabetic(val number: Int) {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9),
    zero(0);

    fun toInt() = number
}

fun getAlphabeticNumber(value: String): NumbersAlphabetic? {
    val trimmed = value.trim()

    trimmed.toIntOrNull()?.let { n ->
        NumbersAlphabetic.entries.firstOrNull { it.number == n }?.let { return it }
    }

    return NumbersAlphabetic.entries.firstOrNull { it.name.equals(trimmed, ignoreCase = true) }
}


data class ConfigForDay(var submit1: Boolean = false,
                        var submit2: Boolean = false,
                        var check1: Boolean = false,
                        var check2: Boolean = false,
                        var checkDemo1: Boolean = false,
                        var checkDemo2: Boolean = false,
                        var execute1: Boolean = false,
                        var execute2: Boolean = false,
                        var execute1demo: Boolean = false,
                        var execute2demo: Boolean = false,
                        var exampleSolution1:Int = 0,
                        var exampleSolution2: Int = 0,
                        var solution1: Int = 0,
                        var solution2: Int = 0)

enum class RotationDirection(val code: Char) {
    LEFT('L')
    ,RIGHT('R');

    companion object {
        fun fromChar(c: Char): RotationDirection? =
            entries.firstOrNull { it.code == c }
    }
}

data class Rotation(val direction: RotationDirection, val distance: Int)

fun extractInstructions(line: String): Rotation? {
    val trimmed = line.trim()
    if (trimmed.isEmpty()) return null

    val dir = RotationDirection.fromChar(trimmed[0]) ?: return null
    val distance = trimmed.substring(1).toIntOrNull() ?: return null

    return Rotation(dir, distance)
}
