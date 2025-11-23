#!/bin/bash

# --- Configuration ---
GRADLE_TASK="./gradlew fetchInput"
# Default to the current year if not provided
CURRENT_YEAR=$(date +%Y)
# ---------------------

# 1. Validate Arguments
if [ -z "$1" ]; then
    echo "Usage: $0 <day> [year]"
    echo "Example: $0 1 - Sets up Day 1 for the current year ($CURRENT_YEAR)."
    echo "Example: $0 5 2023 - Sets up Day 5 for 2023."
    exit 1
fi

DAY=$(printf "%02d" "$1") # Pad day to two digits (01, 02, etc.)
YEAR=${2:-$CURRENT_YEAR}   # Use $2 if provided, otherwise use CURRENT_YEAR

echo "Starting Advent of Code setup for $YEAR Day $DAY..."

# 2. Run the Kotlin Script via Gradle
echo "-> Fetching input and creating Kotlin file..."
# We pass -Pday and -Pyear to the Gradle task
$GRADLE_TASK -Pday="$1" -Pyear="$YEAR"

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

# 3. Add Files to Git
echo "-> Adding new files to Git staging area..."
# Assuming the file structure created by the Kotlin script:
# src/YEAR/days/Day_XX.kt
# src/YEAR/input/day_XX.txt
git add "src/$YEAR/days/Day_$DAY.kt"
git add "src/$YEAR/input/day_$DAY.txt"
# Also adding the newly created year directories if they didn't exist
git add "src/$YEAR/days"
git add "src/$YEAR/input"

# 4. Create the Commit Message
COMMIT_MESSAGE="aoc-$YEAR-$DAY: setup"
echo "-> Creating commit: '$COMMIT_MESSAGE'"
git commit -m "$COMMIT_MESSAGE"

echo "✨ Setup complete. Branch '$TARGET_BRANCH' created/checked out and commit created successfully."
echo "   Run 'git log' to confirm."