package datastructures

enum class MathOperation(val sign: Char) {
    ADD('+'),
    MULTIPLY('*');

    public fun performCalculation(acc: Long,next:Long):Long = when(this) {
        ADD -> acc + next
        MULTIPLY -> acc * next
    }

    companion object {
        fun fromChar(raw: Char): MathOperation? {
            return MathOperation.entries.firstOrNull { it.sign == raw }
        }
    }
}

data class MathOperations(val from: Int, val to: Int, val operation: MathOperation){
    fun length():Int = to - from
}