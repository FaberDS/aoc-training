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

fun extractInstructions(instructions: String): Rotation?{
    val regex = Regex("""([LR])|(\d+)""")

    var currentRotation: RotationDirection? = null

    for (match in regex.findAll(instructions)) {
        val rotToken = match.groups[1]?.value
        val numToken = match.groups[2]?.value

        if (rotToken != null) {
            currentRotation = RotationDirection.fromChar(rotToken[0])
        } else if (numToken != null && currentRotation != null) {
            val steps = numToken.toInt()
            return Rotation(currentRotation, numToken.toInt())
        }
    }
    return null
}
