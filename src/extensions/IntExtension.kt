package extensions

fun Int.binaryStr(): String = this.toString(radix = 2).padStart(8,'0')
