package aoc
import ENV_FILE_PATH
import loadEnvFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AocSubmissionResult(
    val ok: Boolean,
    val status: Status,
    val message: String
) {
    enum class Status {
        CORRECT,
        INCORRECT,
        THROTTLED,
        ERROR
    }
}

fun submitAocAnswer(
    year: Int,
    day: Int,
    level: Int,
    answer: String,
    sessionCookie: String? = null
): AocSubmissionResult {
    val env = loadEnvFile(ENV_FILE_PATH)
    val session = env["AOC_SESSION_COOKIE"] ?: ""
    if (session.isBlank()) {
        val msg = "Missing session cookie. Set AOC_SESSION_COOKIE env var or pass sessionCookie."
        println(msg)
        return AocSubmissionResult(false, AocSubmissionResult.Status.ERROR, msg)
    }

    val url = URL("https://adventofcode.com/$year/day/$day/answer")
    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "POST"
        setRequestProperty(
            "User-Agent",
            "aoc-kotlin-helper (https://github.com/yourname/yourrepo)"
        )
        setRequestProperty("Cookie", "session=$session")
        doOutput = true
    }

    // Form data
    val body = "level=$level&answer=${answer}"
    connection.outputStream.use { os ->
        val input = body.toByteArray(StandardCharsets.UTF_8)
        os.write(input, 0, input.size)
    }

    val responseText = try {
        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream ?: connection.inputStream
        }

        BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8)).use { reader ->
            reader.readText()
        }
    } catch (e: Exception) {
        val msg = "Error talking to Advent of Code: ${e.message}"
        println(msg)
        return AocSubmissionResult(false, AocSubmissionResult.Status.ERROR, msg)
    } finally {
        connection.disconnect()
    }

    // --- Throttled case ---
    if ("You gave an answer too recently" in responseText) {
        val regex = Regex("You have (\\d+)s left to wait")
        val match = regex.find(responseText)

        val msg = if (match != null) {
            val waitSeconds = match.groupValues[1].toLong()
            val nextTime = LocalDateTime.now().plusSeconds(waitSeconds)
            val formatted = nextTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            """
            You are being throttled by Advent of Code.
            Next allowed submission at: $formatted
            (in $waitSeconds seconds)
            """.trimIndent()
        } else {
            "You are being throttled by Advent of Code, but wait time could not be parsed."
        }

        println(msg)
        return AocSubmissionResult(false, AocSubmissionResult.Status.THROTTLED, msg)
    }

    // --- Correct ---
    if ("That's the right answer!" in responseText) {
        val msg = "Correct answer! ⭐"
        println(msg)
        return AocSubmissionResult(true, AocSubmissionResult.Status.CORRECT, msg)
    }

    // --- Incorrect ---
    if ("That's not the right answer" in responseText) {
        val msg = "Incorrect answer."
        println(msg)
        return AocSubmissionResult(false, AocSubmissionResult.Status.INCORRECT, msg)
    }

    // --- Wrong level / already completed, etc. ---
    if ("You don't seem to be solving the right level" in responseText) {
        val msg = "Wrong level (you probably already solved this star)."
        println(msg)
        return AocSubmissionResult(false, AocSubmissionResult.Status.ERROR, msg)
    }

    // --- Fallback ---
    val msg = "Unexpected response from Advent of Code (HTTP ${connection.responseCode})."
    println(msg)
    return AocSubmissionResult(false, AocSubmissionResult.Status.ERROR, msg)
}
