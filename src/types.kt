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