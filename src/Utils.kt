import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()
fun readInput(name: String, year: String = "2024") = Path("src/$year/input/$name.txt").readText().trim().lines()

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
fun splitInput(input: List<String>): Pair<List<Int>,List<Int>> {
    val list1: MutableList<Int> = mutableListOf();
    val list2: MutableList<Int> = mutableListOf();
    for (i in input.indices) {
        val (l,r) = input[i].split("   ")
        list1.add(l.toInt())
        list2.add(r.toInt())
    }
    return Pair(list1, list2)
}

/**
 * Split input string by spaces into int list
 */
fun splitInputToIntList(input: List<String>): List<List<Int>> {
    return input.map { line ->
        line.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }
}


/**
 * Splites a list of Strings into a two-dimensional grid of `Char`'s
 */
fun splitIntoCharGrid(input: List<String>): MutableList<MutableList<Char>> {
    val grid = mutableListOf<MutableList<Char>>()
    for(line in input) {
        val row = line
            .toMutableList()
        grid.add(row)
    }
    return grid
}
/**
 * Finds coordinates for a given `Char` in a two-dimensional grid
 */
fun findCoordsForChar(char: Char, grid: List<List<Char>>): List<Coord> {
    val coords = mutableListOf<Coord>()
    for((i,row) in grid.withIndex()) {
        for((j,col) in row.withIndex()) {
            if(col == char) {
                coords.add(Coord(i,j))
            }
        }
    }
    return coords
}

fun getAlphaNumericRegex(overlapping: Boolean) : Regex {
    val overlappingHandling = if (overlapping) "?=" else ""
    return Regex("""($overlappingHandling(one|two|three|four|five|six|seven|eight|nine|\d))""")
}