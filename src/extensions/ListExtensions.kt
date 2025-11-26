package extensions

fun<T> List<T>.middleValue(): T {
    val n = this.count()
    if (n % 2 == 0) {
        return this.get(n / 2 - 1)
    } else {
        return  this.get(n / 2)
    }
}