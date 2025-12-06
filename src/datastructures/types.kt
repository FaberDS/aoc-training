package datastructures

/**
 * Basic Coordinate type
 */
data class Coord(val row: Int, val col: Int)

enum class NumbersAlphabetic(val value: Int) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    fun toInt(): Int = value

    companion object {
        fun fromString(raw: String): NumbersAlphabetic? {
            val trimmed = raw.trim()

            // numeric form: "1"
            trimmed.toIntOrNull()?.let { n ->
                return entries.firstOrNull { it.value == n }
            }

            // alphabetic form: "one", "Two", etc.
            return entries.firstOrNull {
                it.name.equals(trimmed, ignoreCase = true)
            }
        }
    }
}

fun getAlphabeticNumber(value: String): NumbersAlphabetic? =
    NumbersAlphabetic.fromString(value)


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

enum class AdjacentPositions(val row: Int, val col: Int) {
    TOP_LEFT(-1, -1),
    TOP_CENTER(-1, 0),
    TOP_RIGHT(-1, +1),
    LEFT(0, -1),
    RIGHT(0, +1),
    BOTTOM_LEFT(+1, -1),
    BOTTOM_CENTER(+1, 0),
    BOTTOM_RIGHT(+1, +1);
}

