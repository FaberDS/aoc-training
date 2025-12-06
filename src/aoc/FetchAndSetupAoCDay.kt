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

private fun isGitRepo(): Boolean =
    runCommand("git", "rev-parse", "--is-inside-work-tree") == 0

private fun isWorkingTreeClean(): Boolean =
    runCommandCapture("git", "status", "--porcelain").isBlank()

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
            val dayPadded = AoCPaths.dayPadded(day)
            val todayFile = AoCPaths.solutionFile(currentYear, day)

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

    val dayPadded = AoCPaths.dayPadded(dayUnpadded)
    val branchName = "aoc/$year/$dayPadded"

    // --- 2. Git safety checks BEFORE touching any files ---
    if (isGitRepo()) {
        if (!isWorkingTreeClean()) {
            println("❌ Working tree is not clean. Please commit or stash your changes before running setupDay.")
            return
        }

        if (branchExists(branchName)) {
            println("❌  Branch '$branchName' already exists. Aborting setup to avoid messing with existing work.")
            println("️⚠️  use:    gbd! $branchName")
            println("️⚠️  to delete the branch | or adjust the setup command")
            return
        }
    } else {
        println("⚠️  Not inside a git repository. Will skip git branch/commit steps.")
    }

    println("Starting Advent of Code setup for $year Day $dayPadded...")

    // --- 3. Ensure directories exist ---
    val yearInputDir = AoCPaths.inputDir(year)
    val yearDaysDir  = AoCPaths.daysDir(year)
    val yearTestDir  = AoCPaths.testsDir(year)

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
        println("⚠️  Not a git repo, skipping branch and commit.")
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

    val solutionFilePath = AoCPaths.solutionFile(year, dayUnpadded).path
    val inputFilePath    = AoCPaths.realInputFile(year, dayUnpadded).path
    val demoInputPath    = AoCPaths.demo1File(year, dayUnpadded).path
    val demoInput2Path   = AoCPaths.demo2File(year, dayUnpadded).path
    val testFilePath     = AoCPaths.testFile(year, dayUnpadded).path

    println("-> Adding new files to Git staging area...")
    runCommand("git", "add", solutionFilePath)
    runCommand("git", "add", "-f", inputFilePath)
    runCommand("git", "add", "-f", demoInputPath)
    runCommand("git", "add", "-f", demoInput2Path)
    runCommand("git", "add", testFilePath)
    runCommand("git", "add", AoCPaths.daysDir(year).path)
    runCommand("git", "add", AoCPaths.inputDir(year).path)
    runCommand("git", "add", AoCPaths.testsDir(year).path)

    val commitMessage = "aoc-$year-$dayPadded: setup"
    println("-> Creating commit: '$commitMessage'")
    val commitExit = runCommand("git", "commit", "-m", commitMessage)

    if (commitExit == 0) {
        println("✨ Setup complete. Commit created successfully.")
    } else {
        println("⚠️  git commit failed (maybe no changes). Check 'git status' and commit manually if needed.")
    }

    runCommand("open", AoCPaths.aocUrl(year, dayUnpadded))
}
