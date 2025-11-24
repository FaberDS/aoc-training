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