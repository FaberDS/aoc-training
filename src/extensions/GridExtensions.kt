package extensions

/**
 * Safe getter for a two-dimensional grid
 */
fun <T>List<List<T>>.safeGet(row: Int, col: Int): T? {
    if (row !in 0 until size) return null
    var innerList = this[row]
    if (col !in 0 until innerList.size) return null
    return innerList[col]
}