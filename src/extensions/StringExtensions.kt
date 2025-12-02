package extensions

/**
 * Prints the given text centered within a fixed-width separator block.
 * This is implemented as an extension function on String for idiomatic Kotlin usage.
 *
 * Example Usage:
 * "Part 1 Solution".printSeparated()
 * "Title".printSeparated(width = 50, char = '*')
 *
 * @param width The total width of the separator block (default: 80).
 * @param char The character used for the separator (default: '#').
 */
fun String.printSeparated(width: Int = 80, char: Char = '#') {
    val contentWidth = this.length + 4

    if (width < contentWidth) {
        println("Warning: Separator width ($width) too small for content (${this}). Printing content only.")
        println(this)
        return
    }

    val paddingLength = width - contentWidth
    val leftPadding = paddingLength / 2
    val rightPadding = paddingLength - leftPadding

    val sep = char.toString().repeat(width)

    println()
    println(sep)
    println("${char} ${" ".repeat(leftPadding)}$this${" ".repeat(rightPadding)} ${char}")
    println(sep)
    println()
}

fun String.splitInHalf(): Pair<String, String> {
    val mid = length / 2
    val first = substring(0, mid)
    val second = substring(mid)
    return first to second
}

fun String.allCharsSame(): Boolean {
    return this.toSet().size == 1
}

fun String.findRepeatingUnit(): String? {
    if (length <= 1) return null

    for (len in 1..length / 2) {
        if (length % len != 0) continue

        val unit = substring(0, len)
        if (unit.repeat(length / len) == this) {
            return unit   // e.g. "56" for "565656"
        }
    }
    return null
}

fun String.isRepeatingPattern(): Boolean =
    findRepeatingUnit() != null