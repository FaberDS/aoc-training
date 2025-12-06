package aoc

import java.io.File
import java.time.LocalDate

private fun runCommand(vararg cmd: String): Int {
    val process = ProcessBuilder(*cmd)
        .inheritIO()
        .start()
    return process.waitFor()
}

private fun runCommandCapture(vararg cmd: String): String {
    val process = ProcessBuilder(*cmd)
        .redirectErrorStream(true)
        .start()
    return process.inputStream.bufferedReader().readText()
}

/** Are we inside a git repo at all? */
private fun isGitRepo(): Boolean =
    runCommand("git", "rev-parse", "--is-inside-work-tree") == 0

/** Is the working tree clean (no uncommitted changes)? */
private fun isWorkingTreeClean(): Boolean =
    runCommandCapture("git", "status", "--porcelain").isBlank()

/** Does the given branch already exist? */
private fun branchExists(name: String): Boolean =
    runCommand("git", "rev-parse", "--verify", "--quiet", name) == 0

fun main(args: Array<String>) {
    val now = LocalDate.now()
    val currentYear = now.year
    val currentMonth = now.monthValue
    val currentDay = now.dayOfMonth

    // --- 1. Determine DAY and YEAR (with auto-mode for December) ---
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
    val branchName = "aoc/$year/$dayPadded"

    // --- 2. Git safety checks BEFORE touching any files ---
    if (isGitRepo()) {
        if (!isWorkingTreeClean()) {
            println("❌ Working tree is not clean. Please commit or stash your changes before running setupDay.")
            return
        }

        if (branchExists(branchName)) {
            println("❌ Branch '$branchName' already exists. Aborting setup to avoid messing with existing work.")
            return
        }
    } else {
        println("⚠️ Not inside a git repository. Will skip git branch/commit steps.")
    }

    println("Starting Advent of Code setup for $year Day $dayPadded...")

    // --- 3. Ensure directories exist ---
    val yearInputDir = File("src/$year/input")
    val yearDaysDir  = File("src/$year/days")
    val yearTestDir  = File("src/test/$year")

    println("-> Ensuring directories exist: ${yearInputDir.path}, ${yearDaysDir.path}, ${yearTestDir.path}")
    yearInputDir.mkdirs()
    yearDaysDir.mkdirs()
    yearTestDir.mkdirs()

    // --- 4. Fetch input ---
    val env = loadEnvFile(ENV_FILE_PATH)
    val sessionCookie = env["AOC_SESSION_COOKIE"] ?: ""

    println("-> Fetching input...")
    val fetched = fetchInputAndSave(dayUnpadded, year, sessionCookie)
    if (!fetched) {
        println("Skipping file creation and git steps because input fetch failed.")
        return
    }

    // --- 5. Create solution, test, and demo files ---
    println("-> Creating solution, test, and demo files...")
    createKotlinFiles(dayUnpadded, year)

    // --- 6. Git automation: only if we're in a repo ---
    if (!isGitRepo()) {
        println("⚠️ Not a git repo, skipping branch and commit.")
        return
    }

    println("-> Creating and switching to branch: $branchName")
    val checkoutExit = runCommand("git", "checkout", "-b", branchName)
    if (checkoutExit != 0) {
        println("   (Branch may already exist or checkout failed; stopping to avoid committing on the wrong branch.)")
        return
    }

    println("-> Setting Git identity for commit...")
    runCommand("git", "config", "user.email", "github-actions[bot]@users.noreply.github.com")
    runCommand("git", "config", "user.name", "GitHub Actions Bot")

    val solutionFilePath = "src/$year/days/Day_${dayPadded}__${year}.kt"
    val inputFilePath    = "src/$year/input/day_${dayPadded}.txt"
    val demoInputPath    = "src/$year/input/day_${dayPadded}_demo.txt"
    val testFilePath     = "src/test/$year/Day_${dayPadded}__${year}_Test.kt"

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
