package extensions

fun<T> List<T>.middleValue(): T {
    val n = this.count()
    if (n % 2 == 0) {
        return this.get(n / 2 - 1)
    } else {
        return  this.get(n / 2)
    }
}

fun List<CharArray>.toDebugString(): String =
    this.joinToString("\n") { it.concatToString() }

fun List<List<Char>>.toDebugStr(): String =
    this.joinToString("\n") { it.joinToString("") }

fun MutableList<Boolean>.flip(index: Int) {
    require(index in indices) { "Index $index out of bounds for size $size" }
    this[index] = !this[index]
}

fun <T> diffIndices(a: List<T>, b: List<T>): List<Int> {
    val maxSize = maxOf(a.size, b.size)
    val result = mutableListOf<Int>()

    for (i in 0 until maxSize) {
        val av = a.getOrNull(i)
        val bv = b.getOrNull(i)
        if (av != bv) {
            result += i
        }
    }
    return result.toList()
}

fun <T> List<T>.allSubsets(): List<List<T>> =
    this.fold(listOf(emptyList())) { acc, elem ->
        acc + acc.map { it + elem }
    }