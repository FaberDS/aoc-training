package extensions

fun Long.isInvalidId(): Boolean {
    val s = this.toString()
    val n = s.length
    if (n < 2) return false

    for (len in 1..n / 2) {
        if (n % len != 0) continue

        val unit = s.substring(0, len)
        val times = n / len
        val matchString = unit.repeat(times) == s
//            utils.println("isInvalid: s: $s, n: $n, unit: $unit, times: $times | $matchString")
        // times is >= 2 here because len <= n/2
        if (matchString) {
            return true
        }
    }
    return false
}