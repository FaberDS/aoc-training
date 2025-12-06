package aoc

fun main(args: Array<String>) {
    val (day, year) = resolveDayYearFromArgs(args)
    val env = loadEnvFile(ENV_FILE_PATH)
    val sessionCookie = env["AOC_SESSION_COOKIE"] ?: ""

    fetchInputAndSave(day, year, sessionCookie)
}