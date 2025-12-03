package extensions

fun <K, V> Map<K, V>.firstElement(): Pair<K, V> =
    this.entries.first().let { it.key to it.value }

fun <K, V> Map<K, V>.removeFirstElement(): Pair<Pair<K, V>, Map<K, V>> {
    val first = this.entries.first().let { it.key to it.value }
    val rest = this.entries
        .drop(1)
        .associate { it.key to it.value }
    return first to rest
}