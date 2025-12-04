#!/bin/bash

# --- Configuration ---
GRADLE_TASK="./gradlew fetchInput"
CURRENT_YEAR=$(date +%Y)
CURRENT_MONTH=$(date +%m)
CURRENT_DAY=$(date +%d)
# ---------------------

usage() {
    echo "Usage: $0 <day> [year]"
    echo "Example: $0 1        - Sets up Day 1 for the current year ($CURRENT_YEAR)."
    echo "Example: $0 5 2023   - Sets up Day 5 for 2023."
}

# 1. Determine DAY and YEAR (with auto-mode for December)
if [ -z "$1" ]; then
    # No parameters -> try "today" logic
    if [ "$CURRENT_MONTH" = "12" ]; then
        DAY=$(printf "%02d" "$CURRENT_DAY")
        DAY_UNPAD="$CURRENT_DAY"
        YEAR="$CURRENT_YEAR"

        # ⚠️ Match your actual filename pattern: Day_03__2025.kt
        TODAY_FILE="src/$YEAR/days/Day_${DAY}__${YEAR}.kt"

        if [ -e "$TODAY_FILE" ]; then
            echo "Setup already exists for YEAR=$YEAR, DAY=$DAY:"
            echo "-> $TODAY_FILE"
            echo "Nothing to do. Exiting."
            exit 0
        else
            echo "No parameters provided; auto-detected Advent of Code date:"
            echo "-> Using YEAR=$YEAR, DAY=$DAY (today)."
        fi
    else
        usage
        exit 1
    fi
else
    DAY=$(printf "%02d" "$1")      # Pad day to two digits (01, 02, etc.)
    YEAR=${2:-$CURRENT_YEAR}       # Use $2 if provided, otherwise use CURRENT_YEAR
    DAY_UNPAD="$1"
fi

echo "Starting Advent of Code setup for $YEAR Day $DAY..."

# --- 1. Define Paths ---
YEAR_INPUT_DIR="src/$YEAR/input"
YEAR_DAYS_DIR="src/$YEAR/days"

# --- NEW STEP: Create necessary year directories if they don't exist ---
echo "-> Ensuring directories exist: $YEAR_INPUT_DIR and $YEAR_DAYS_DIR"
mkdir -p "$YEAR_INPUT_DIR" "$YEAR_DAYS_DIR"
# The '-p' flag ensures that parent directories (like src/YEAR) are created
# and suppresses errors if the directories already exist.

# 2. Run the Kotlin Script via Gradle
echo "-> Fetching input and creating Kotlin file..."
# We pass -Pday and -Pyear to the Gradle task
$GRADLE_TASK -Pday="$DAY_UNPAD" -Pyear="$YEAR"

# Check the exit status of the Gradle command
if [ $? -ne 0 ]; then
    echo "ERROR: Gradle task failed. Check the output above."
    exit 1
fi

# --- GIT AUTOMATION: Create Branch and Commit ---

TARGET_BRANCH="aoc/$YEAR/$DAY"
echo "-> Creating and switching to branch: $TARGET_BRANCH"

# Use 'git checkout -b' to create and switch, which will fail gracefully
# if the branch already exists, meaning we stay on that branch.
git checkout -b "$TARGET_BRANCH"

# NEW FIX 1: Set Git identity for the bot user
echo "-> Setting Git identity for commit..."
git config user.email "github-actions[bot]@users.noreply.github.com"
git config user.name "GitHub Actions Bot"


# 3. Add Files to Git
echo "-> Adding new files to Git staging area..."
# Assuming the file structure created by the Kotlin script:
# src/YEAR/days/Day_XX.kt
# src/YEAR/input/day_XX.txt
git add "$YEAR_DAYS_DIR/Day_$DAY.kt"
# Force add the input file to override .gitignore
git add -f "$YEAR_INPUT_DIR/day_$DAY.txt"
# ADD NEW FILE: Force add the demo input file (day_XX_demo.txt)
git add -f "$YEAR_INPUT_DIR/day_${DAY}_demo.txt"

# We no longer need to explicitly add the directories, but we'll add the
# base folders to ensure Git tracks them correctly if they were just created.
git add "$YEAR_DAYS_DIR"
git add "$YEAR_INPUT_DIR"

# 4. Create the Commit Message
COMMIT_MESSAGE="aoc-$YEAR-$DAY: setup"
echo "-> Creating commit: '$COMMIT_MESSAGE'"
git commit -m "$COMMIT_MESSAGE"

echo "✨ Setup complete. Commit created successfully. Run 'git log' to confirm."