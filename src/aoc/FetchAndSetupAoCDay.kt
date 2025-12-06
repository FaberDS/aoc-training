package aoc

import java.io.File
import java.time.LocalDate

private fun runCommand(vararg cmd: String): Int {
    val process = ProcessBuilder(*cmd)
        .inheritIO()
        .start()
    return process.waitFor()
}

fun main(args: Array<String>) {
    val now = LocalDate.now()
    val currentYear = now.year
    val currentMonth = now.monthValue
    val currentDay = now.dayOfMonth

    val (dayUnpadded, year) = if (args.isEmpty()) {
        if (currentMonth == 12) {
            val day = currentDay
            val dayPadded = "%02d".format(day)
            val todayFile = File("src/$currentYear/days/Day_${dayPadded}__${currentYear}.kt")

            if (todayFile.exists()) {
                println("Setup already exists for YEAR=$currentYear, DAY=$dayPadded:")
                println("-> ${todayFile.path}")
                return
            } else {
                println("No parameters provided; auto-detected Advent of Code date:")
                println("-> Using YEAR=$currentYear, DAY=$dayPadded (today).")
            }
            day to currentYear
        } else {
            println("Usage: pass <day> [year], or run with no args in December for auto-mode.")
            return
        }
    } else {
        val day = args[0].toInt()
        val yearValue = if (args.size > 1) args[1].toInt() else currentYear
        day to yearValue
    }

    val dayPadded = "%02d".format(dayUnpadded)
    println("Starting Advent of Code setup for $year Day $dayPadded...")

    val yearInputDir = File("src/$year/input")
    val yearDaysDir  = File("src/$year/days")
    val yearTestDir  = File("src/test/$year")

    println("-> Ensuring directories exist: ${yearInputDir.path}, ${yearDaysDir.path}, ${yearTestDir.path}")
    yearInputDir.mkdirs()
    yearDaysDir.mkdirs()
    yearTestDir.mkdirs()

    val env = loadEnvFile(ENV_FILE_PATH)
    val sessionCookie = env["AOC_SESSION_COOKIE"] ?: ""

    println("-> Fetching input...")
    val fetched = fetchInputAndSave(dayUnpadded, year, sessionCookie)
    if (!fetched) {
        println("Skipping file creation and git steps because input fetch failed.")
        return
    }

    println("-> Creating solution, test, and demo files...")
    createKotlinFiles(dayUnpadded, year)

    val branchName = "aoc/$year/$dayPadded"
    println("-> Creating and switching to branch: $branchName")
    val checkoutExit = runCommand("git", "checkout", "-b", branchName)
    if (checkoutExit != 0) {
        println("   (Branch may already exist; staying on current branch.)")
    }

    println("-> Setting Git identity for commit...")
    runCommand("git", "config", "user.email", "github-actions[bot]@users.noreply.github.com")
    runCommand("git", "config", "user.name", "GitHub Actions Bot")

    val solutionFilePath = "src/$year/days/Day_${dayPadded}__${year}.kt"
    val inputFilePath    = "src/$year/input/day_${dayPadded}.txt"
    val demoInputPath    = "src/$year/input/day_${dayPadded}_demo.txt"
    val testFilePath     = "src/test/$year/${year}_${dayPadded}Test.kt"

    println("-> Adding new files to Git staging area...")
    runCommand("git", "add", solutionFilePath)
    runCommand("git", "add", "-f", inputFilePath)
    runCommand("git", "add", "-f", demoInputPath)
    runCommand("git", "add", testFilePath)
    runCommand("git", "add", "src/$year/days")
    runCommand("git", "add", "src/$year/input")
    runCommand("git", "add", "src/test/$year")

    val commitMessage = "aoc-$year-$dayPadded: setup"
    println("-> Creating commit: '$commitMessage'")
    val commitExit = runCommand("git", "commit", "-m", commitMessage)

    if (commitExit == 0) {
        println("✨ Setup complete. Commit created successfully.")
    } else {
        println("⚠️ git commit failed (maybe no changes). Check 'git status' and commit manually if needed.")
    }
}
