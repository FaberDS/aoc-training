import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputLocal(name: String): List<String> =
    Path("src/$name.txt").readText().trim().lines()

fun readInput(name: String, year: String = "2024"): List<String> =
    Path("src/$year/input/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Split input string by spaces into two lists of Int
 */
fun splitInput(input: List<String>): Pair<List<Int>, List<Int>> {
    val (left, right) = input
        .map { line ->
            val (l, r) = line.split("   ")
            l.toInt() to r.toInt()
        }
        .unzip()

    return left to right
}

/**
 * Split input string by spaces into int list
 */
fun splitInputToIntList(input: List<String>): List<List<Int>> =
    input.map { line ->
        line
            .split(' ')
            .filter(String::isNotBlank)
            .map(String::toInt)
    }


fun splitInputToIntPositionMap(
    input: List<String>,
    descending: Boolean = true
): List<Map<Int, Int>> {
    return input.map { row ->
        val pairs = row
            .filter { !it.isWhitespace() }
            .mapIndexed { index, ch ->
                val number = ch.digitToInt()
               index to number
            }

        val sortedPairs = if (descending) {
            pairs.sortedByDescending { it.second }
        } else {
            pairs.sortedBy { it.second }
        }
        sortedPairs.toMap()
    }
}
/**
 * Splites a list of Strings into a two-dimensional grid of `Char`'s
 */
fun splitIntoCharGrid(input: List<String>): List<List<Char>> {
    val grid = mutableListOf<MutableList<Char>>()
    for(line in input) {
        val row = line.toMutableList()
        grid.add(row)
    }
    return grid
}

/**
 * Finds coordinates for a given `Char` in a two-dimensional grid
 */
fun findCoordsForChar(char: Char, grid: List<List<Char>>): List<Coord> =
    grid.flatMapIndexed { rowIndex, row ->
        row.mapIndexedNotNull { colIndex, c ->
            if (c == char) Coord(rowIndex, colIndex) else null
        }
    }

private val numberWords = listOf(
    "one", "two", "three", "four", "five",
    "six", "seven", "eight", "nine"
)

fun getAlphaNumericRegex(overlapping: Boolean): Regex {
    val overlap = if (overlapping) "?=" else ""
    val words = numberWords.joinToString("|")
    return Regex("""($overlap($words|\d))""")
}