import java.io.File
import java.net.URL
import java.util.*

// --- CONFIGURATION ---
const val AOC_YEAR = 2024
const val AOC_INPUT_BASE_URL = "https://adventofcode.com"
const val BASE_PATH = "src/2024/input"
const val ENV_FILE_PATH = ".env"
// ---------------------

/**
 * Manually parses a simple .env file to load key-value pairs.
 */
fun loadEnvFile(path: String): Map<String, String> {
    val envMap = mutableMapOf<String, String>()
    try {
        File(path).forEachLine { line ->
            // Trim whitespace, skip comments and blank lines
            val trimmedLine = line.trim()
            if (trimmedLine.isBlank() || trimmedLine.startsWith('#')) return@forEachLine

            // Simple key=value parsing
            val parts = trimmedLine.split('=', limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                // Remove surrounding quotes and trim
                val value = parts[1].trim().trim { it == '"' || it == '\'' }
                envMap[key] = value
            }
        }
    } catch (e: Exception) {
        println("Warning: Could not read $path. Make sure the file exists.")
    }
    return envMap
}

fun fetchInputAndSave(day: Int, sessionCookieValue: String) {
    if (sessionCookieValue.isBlank()) {
        println("ERROR: AOC_SESSION_COOKIE is empty. Please set your session cookie in the .env file.")
        return
    }

    val dayPadded = String.format("%02d", day)
    val urlString = "$AOC_INPUT_BASE_URL/$AOC_YEAR/day/$day/input"
    val outputFileName = "day_$dayPadded.txt"
    val outputPath = File(BASE_PATH, outputFileName)

    try {
        println("Fetching input for Day $day...")
        val url = URL(urlString)

        // Open connection and set the session cookie
        val connection = url.openConnection()
        connection.setRequestProperty("Cookie", "session=$sessionCookieValue")

        // Read the input content
        val inputStream = connection.getInputStream()
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        val inputContent = if (scanner.hasNext()) scanner.next() else ""

        if (inputContent.isBlank()) {
            println("WARNING: Fetched empty input. Check the day number and your session cookie.")
        }

        // Ensure the directory exists
        outputPath.parentFile.mkdirs()

        // Write the content to the file
        outputPath.writeText(inputContent.trim())

        println("✅ Successfully saved input for Day $day to: ${outputPath.path}")

    } catch (e: Exception) {
        println("❌ Failed to fetch or save input for Day $day.")
        println("Error: ${e.message}")
        // Detailed stack trace is suppressed for brevity unless necessary
    }
}

fun main(args: Array<String>) {
    // 1. Load environment variables
    val env = loadEnvFile(ENV_FILE_PATH)
    val sessionCookie = env["AOC_SESSION_COOKIE"] ?: ""

    // 2. Determine the day number
    val day = when {
        args.isNotEmpty() -> args[0].toIntOrNull()
        else -> {
            print("Please enter the Advent of Code day number (1-25): ")
            readLine()?.toIntOrNull()
        }
    }

    // 3. Fetch input
    when (day) {
        in 1..25 -> fetchInputAndSave(day!!, sessionCookie)
        else -> println("Invalid day number. Please enter a number between 1 and 25.")
    }
}