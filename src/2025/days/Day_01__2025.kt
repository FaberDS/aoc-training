/**
 * Advent of code (2025) solution for day 1 by Denis Schüle.
 * [Advent of code 2025-1 ](https://adventofcode.com/2025/day/1)
 **/
import Rotation
import RotationDirection
import aoc.handleSubmit
import com.sun.org.apache.xpath.internal.operations.Bool
import extensions.printSeparated
import jdk.javadoc.internal.tool.Main.execute
import java.util.Collections.rotate
import kotlin.math.abs
import kotlin.time.measureTimedValue


fun main() {


     val config = ConfigForDay(
             submit1 = false,
             submit2 = false,
             check1 = true,
             check2 = true,
             checkDemo1 = true,
             checkDemo2 = true,
             execute1 = true,
             execute2 = true,
             execute1demo = true,
             execute2demo = true,
             exampleSolution1 = 3,
             exampleSolution2 = 6,
             solution1 = 1092,
             solution2 = 6616) //wrong: 2624, 4936,7708

    fun rotate(rotation: Rotation, position: Int): Pair<Int, Int> {
        val step = if (rotation.direction == RotationDirection.RIGHT) 1 else -1
        var pos = position
        var passedZero = 0

        repeat(rotation.distance) {
            pos = (pos + step + 100) % 100
            if (pos == 0) {
                passedZero++
            }
        }
        return pos to passedZero
    }

    fun getIntstructions(input: List<String>): List<Rotation> {
        val rotations = mutableListOf<Rotation>()
        for (line in input) {
            val instruction = extractInstructions(line)
            if(instruction != null) {
                rotations.add(instruction)

            }
        }
        return rotations
    }

    fun applyInstruction(instructions: List<Rotation>, initialPosition: Int = 50, countEveryZeroPassing: Boolean = false ): Int {
        var position = initialPosition
        var result = 0
        for (instruction in instructions) {
            val (newPosition, zerosThisRotation) = rotate(instruction, position)
            if(countEveryZeroPassing) {
                result += zerosThisRotation
            } else if(newPosition == 0) {
                result++
            }
            position = newPosition
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val instructions = getIntstructions(input)
        val result = applyInstruction(instructions)
        return result
    }

    fun part2(input: List<String>): Int {
        val instructions = getIntstructions(input)
        val result = applyInstruction(instructions, countEveryZeroPassing = true)
        return result
    }

    try {

        val exampleInput1 = readInput("day_01_demo", "2025")
        val exampleInput2 = readInput("day_01_demo", "2025")
        /* --- TEST DEMO INPUT --- */
        if(config.execute1demo) {
            val part1DemoSolution = part1(exampleInput1)
            "Part 1 Demo".printSeparated()
            println("- Part 1 Demo: $part1DemoSolution")
            if(config.checkDemo1) check(part1DemoSolution == config.exampleSolution1)
        }
        if(config.execute2demo) {
            "Part 2 Demo".printSeparated()
            val part2DemoSolution = part2(exampleInput2)
            println("- Part 2 Demo: $part2DemoSolution")
            if(config.checkDemo2) check(part2DemoSolution == config.exampleSolution2)
        }



        /* --- RUN FULL INPUT --- */
        if(config.execute1) {
            "Part 1".printSeparated()
            val (part1Solution, timeTakenPart1) = measureTimedValue {
                val input = readInput("day_01", "2025")
               part1(input)
            }
            println("- Part 1: $part1Solution in $timeTakenPart1")
            if(config.check1) check(part1Solution == config.solution1)
            if(config.submit1) handleSubmit(2025,1,1,part1Solution.toString())
        }

        if(config.execute2) {
            "Part 2".printSeparated()
            val (part2Solution, timeTakenPart2) = measureTimedValue {
                val input = readInput("day_01", "2025")
                part2(input)
            }
            println("- Part 2: $part2Solution in $timeTakenPart2")
            if (config.check2) check(part2Solution == config.solution2)
            if (config.submit2) handleSubmit(2025, 1, 2, part2Solution.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

