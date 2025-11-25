import java.io.File
import java.net.URI
import java.util.*

// --- CONFIGURATION ---
// AOC_YEAR is now a variable passed to main and functions.
const val AOC_INPUT_BASE_URL = "https://adventofcode.com"
const val ENV_FILE_PATH = ".env"
// ---------------------

const val KOTLIN_TEMPLATE_FILE_PATH = "aoc_kotlin_template.txt"
// ---------------------

// Remove the old val KOTLIN_TEMPLATE = """...""" block

/**
 * Loads the Kotlin template content from the external file.
 */
fun loadKotlinTemplate(path: String = KOTLIN_TEMPLATE_FILE_PATH): String {
    // File(path).readText() is the simplest way to read the entire file content
    return try {
        File(path).readText().trim()
    } catch (e: Exception) {
        // Handle the case where the template file is missing or unreadable
        System.err.println("FATAL ERROR: Could not read Kotlin template file at $path.")
        System.err.println("Ensure 'kotlin_template.txt' exists in the project root.")
        throw e // Re-throw to halt execution
    }
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
        println(e.printStackTrace())
    }
    return envMap
}

fun fetchInputAndSave(day: Int, year: Int, sessionCookieValue: String): Boolean {
    if (sessionCookieValue.isBlank()) {
        println("ERROR: AOC_SESSION_COOKIE is empty. Please set your session cookie in the .env file.")
        return false
    }

    val dayPadded = String.format("%02d", day)
    val urlString = "$AOC_INPUT_BASE_URL/$year/day/$day/input"

    // Dynamically calculate paths based on the year
    val INPUT_PATH = "src/$year/input"
    // Input filename: day_XX.txt (correct)
    val outputFileName = "day_$dayPadded.txt"
    val outputPath = File(INPUT_PATH, outputFileName)

    try {
        println("Fetching input for Day $day, Year $year...")

        // Use URI to create the URL, avoiding the deprecated constructor (Warning fix)
        val url = URI(urlString).toURL()

        val connection = url.openConnection()
        connection.setRequestProperty("Cookie", "session=$sessionCookieValue")

        val inputStream = connection.getInputStream()
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        val inputContent = if (scanner.hasNext()) scanner.next() else ""

        if (inputContent.isBlank()) {
            println("WARNING: Fetched empty input. Check the day number and your session cookie.")
        }

        outputPath.parentFile.mkdirs()
        outputPath.writeText(inputContent.trim())

        println("✅ Successfully saved input for Day $day to: ${outputPath.path}")
        return true

    } catch (e: Exception) {
        println("❌ Failed to fetch or save input for Day $day, Year $year.")
        println("Error: ${e.message}")
        return false
    }
}

fun createKotlinFile(day: Int, year: Int) {
    val dayPadded = String.format("%02d", day)
    val dayNum = day.toString()

    // Dynamically calculate path based on the year
    val DAYS_PATH = "src/$year/days"
    val INPUT_PATH = "src/$year/input"

    // Output file name: Day_02.kt
    val outputFileName = "Day_$dayPadded.kt"
    val outputPath = File(DAYS_PATH, outputFileName)

    // Demo input file name: day_02_demo.txt
    val demoFileName = "day_${dayPadded}_demo.txt"
    val demoOutputPath = File(INPUT_PATH, demoFileName)


    if (outputPath.exists()) {
        println("⚠️ Solution file already exists: ${outputPath.path}")
    } else {
        val templateContent = loadKotlinTemplate()
        // Perform replacements
        val content = templateContent
            .replace("@@DAY_NUM_PADDED@@", dayPadded)
            .replace("@@DAY_NUM@@", dayNum)
            .replace("@@AOC_YEAR@@", year.toString()) // Inject the year
            .trim()

        outputPath.parentFile.mkdirs()
        outputPath.writeText(content)

        println("📝 Successfully created solution file: ${outputPath.path}")
    }

    // Create the empty demo file if it doesn't exist
    if (!demoOutputPath.exists()) {
        demoOutputPath.writeText("# Add your demo input here\n")
        println("📝 Successfully created demo input file: ${demoOutputPath.path}")
    }
}

fun main(args: Array<String>) {
    val env = loadEnvFile(ENV_FILE_PATH)
    val sessionCookie = env["AOC_SESSION_COOKIE"] ?: ""
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    // --- 1. Determine Day ---
    val day = when {
        // Check for the day argument (position 0)
        args.isNotEmpty() -> args[0].toIntOrNull()
        else -> {
            print("Please enter the Advent of Code day number (1-25): ")
            readLine()?.toIntOrNull()
        }
    }

    // --- 2. Determine Year ---
    val year = when {
        // Check for the year argument (position 1)
        args.size > 1 -> args[1].toIntOrNull() ?: currentYear
        else -> {
            print("Please enter the Advent of Code year (default: $currentYear): ")
            readLine()?.toIntOrNull() ?: currentYear
        }
    }

    // 3. Execute
    when (day) {
        in 1..25 -> {
            val success = fetchInputAndSave(day!!, year, sessionCookie)
            if (success) {
                createKotlinFile(day, year)
            }
        }
        else -> println("Invalid day number. Please enter a number between 1 and 25.")
    }
}