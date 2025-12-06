package enums

enum class AoCColor {
    RED, BLUE, GREEN;

    companion object {
        fun fromString(value: String): AoCColor =
            AoCColor.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: error("Unknown color: $value")
    }
}