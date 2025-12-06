package aoc

import java.io.File
import java.net.URI
import java.util.*

const val AOC_INPUT_BASE_URL = "https://adventofcode.com"
const val ENV_FILE_PATH = ".env"

const val KOTLIN_TEMPLATE_FILE_PATH = "templates/aoc_day_template.txt"
const val KOTLIN_TEST_TEMPLATE_FILE_PATH = "templates/aoc_day_test_template.txt"

/**
 * Load any text template from disk.
 */
fun loadKotlinTemplate(path: String): String =
    try {
        File(path).readText().trim()
    } catch (e: Exception) {
        System.err.println("FATAL: Could not read template file at $path.")
        throw e
    }

fun loadEnvFile(path: String): Map<String, String> {
    val envMap = mutableMapOf<String, String>()
    try {
        File(path).forEachLine { line ->
            val trimmedLine = line.trim()
            if (trimmedLine.isBlank() || trimmedLine.startsWith('#')) return@forEachLine
            val parts = trimmedLine.split('=', limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim().trim { it == '"' || it == '\'' }
                envMap[key] = value
            }
        }
    } catch (e: Exception) {
        println("Warning: Could not read $path. Make sure the file exists.")
        e.printStackTrace()
    }
    return envMap
}

/**
 * Fetches AoC input for day/year and saves to src/<year>/input/day_XX.txt
 */
fun fetchInputAndSave(day: Int, year: Int, sessionCookieValue: String): Boolean {
    if (sessionCookieValue.isBlank()) {
        println("ERROR: AOC_SESSION_COOKIE is empty. Please set it in .env.")
        return false
    }

    val dayPadded = "%02d".format(day)
    val urlString = "$AOC_INPUT_BASE_URL/$year/day/$day/input"

    val inputDir = File("src/$year/input")
    val outputFile = File(inputDir, "day_$dayPadded.txt")

    return try {
        println("Fetching input for Day $day, Year $year...")

        val url = URI(urlString).toURL()
        val connection = url.openConnection()
        connection.setRequestProperty("Cookie", "session=$sessionCookieValue")

        val inputStream = connection.getInputStream()
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        val inputContent = if (scanner.hasNext()) scanner.next() else ""

        if (inputContent.isBlank()) {
            println("WARNING: Fetched empty input. Check the day and your session cookie.")
        }

        inputDir.mkdirs()
        outputFile.writeText(inputContent.trim())

        println("✅ Saved input to: ${outputFile.path}")
        true
    } catch (e: Exception) {
        println("❌ Failed to fetch/save input for Day $day, Year $year.")
        println("Error: ${e.message}")
        false
    }
}

/**
 * Creates:
 *  - solution file: src/<year>/days/Day_XX__<year>.kt
 *  - test file:     src/test/<year>/<year>_XXTest.kt
 *  - demo input:    src/<year>/input/day_XX_demo.txt
 */
fun createKotlinFiles(day: Int, year: Int) {
    val dayPadded = "%02d".format(day)
    val dayNum = day.toString()

    val daysDir = File("src/$year/days")
    val inputDir = File("src/$year/input")
    val testDir = File("src/test/$year")

    val solutionFile = File(daysDir, "Day_${dayPadded}__$year.kt")
    val testFile = File(testDir, "Day_${dayPadded}__${year}_Test.kt")
    val demoFile = File(inputDir, "day_${dayPadded}_demo.txt")

    // --- solution file ---
    if (solutionFile.exists()) {
        println("⚠️ Solution file already exists: ${solutionFile.path}")
    } else {
        val template = loadKotlinTemplate(KOTLIN_TEMPLATE_FILE_PATH)
        val content = template
            .replace("@@DAY_NUM_PADDED@@", dayPadded)
            .replace("@@DAY_NUM@@", dayNum)
            .replace("@@AOC_YEAR@@", year.toString())
            .trim()

        daysDir.mkdirs()
        solutionFile.writeText(content + "\n")
        println("📝 Created solution file: ${solutionFile.path}")
    }

    // --- test file ---
    if (testFile.exists()) {
        println("⚠️ Test file already exists: ${testFile.path}")
    } else {
        val testTemplate = loadKotlinTemplate(KOTLIN_TEST_TEMPLATE_FILE_PATH)
        val testContent = testTemplate
            .replace("@@DAY_NUM_PADDED@@", dayPadded)
            .replace("@@DAY_NUM@@", dayNum)
            .replace("@@AOC_YEAR@@", year.toString())
            .trim()

        testDir.mkdirs()
        testFile.writeText(testContent + "\n")
        println("📝 Created test file: ${testFile.path}")
    }

    // --- demo input ---
    if (!demoFile.exists()) {
        inputDir.mkdirs()
        demoFile.writeText("# Add your demo input here\n")
        println("📝 Created demo input file: ${demoFile.path}")
    }
}

/**
 * Shared helper to resolve (day, year) from CLI args.
 * Same rules as before: args[0]=day, args[1]=year (optional).
 */
fun resolveDayYearFromArgs(args: Array<String>): Pair<Int, Int> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    val day = when {
        args.isNotEmpty() -> args[0].toIntOrNull()
        else -> {
            print("Please enter the day (1-25): ")
            readLine()?.toIntOrNull()
        }
    } ?: error("Invalid or missing day")

    val year = when {
        args.size > 1 -> args[1].toIntOrNull() ?: currentYear
        else -> {
            print("Please enter the year (default: $currentYear): ")
            readLine()?.toIntOrNull() ?: currentYear
        }
    }

    require(day in 1..25) { "Day must be between 1 and 25" }
    return day to year
}
