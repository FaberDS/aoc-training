/**
 * Advent of code (2025) solution for day 9 by Denis Schüle.
 * https://adventofcode.com/2025/day/9
 */
import aoc.AoCDay
import datastructures.Point
import datastructures.getRectangleAreaTo
import datastructures.toPoints

/**
 *
 *  help please not https://arxiv.org/pdf/1708.02758
 */

fun rectangleIntersectsPolygon(a: Point, b: Point, poly: List<Point>): Boolean {
    val x1 = minOf(a.x, b.x)
    val x2 = maxOf(a.x, b.x)
    val y1 = minOf(a.y, b.y)
    val y2 = maxOf(a.y, b.y)

    fun edgeAway(p: Point, q: Point): Boolean {
        val maxX = maxOf(p.x, q.x)
        val minX = minOf(p.x, q.x)
        val maxY = maxOf(p.y, q.y)
        val minY = minOf(p.y, q.y)

//        println("p: $p | q: $q | x1: $x1, y1: $y1, x2: $x2, y2: $y2 maxX: $maxX, minX: $minX, maxY: $maxY minY: $minY | maxX <= x1: ${maxX <= x1} | minX >= x2: ${minX >= x2} | maxY <= y1: ${maxY <= y1} | minY >= y2: ${minY >= y2}")
        return (maxX <= x1) ||     // left of or on left border
                (minX >= x2) ||    // right of or on right border
                (maxY <= y1) ||    // below or on bottom border
                (minY >= y2)       // above or on top border
    }

    val n = poly.size
    // check all edges
    for (i in 0 until n) {
        val p = poly[i]
        val q = poly[(i + 1) % n]

        if (!edgeAway(p, q)) {
            return true
        }
    }
    return false
}

object Day09 : AoCDay<Long>(
    year = 2025,
    day = 9,
    example1Expected = 50,
    example2Expected = 24,
    answer1Expected = 4755278336,
    answer2Expected = 1534043700,
) {

    override fun part1(input: List<String>): Long {
        var bestArea = 0L
        val pts = input.toPoints()

        for (pt in pts) {
            for (pt2 in pts.drop(1)) {
                val area = pt.getRectangleAreaTo(pt2)
                if (area > bestArea) {
                    bestArea = area
                }
            }
        }
        return bestArea
    }

    override fun part2(input: List<String>): Long {
        val pts = input.toPoints()
        var bestArea = 0L

        for (i in 0 until pts.size) {
            // from start to end draw rectangles
            // for each where area is > maxArea check all points
            // if the build a edge which hurts the reactanlge
            val a = pts[i]
            for (j in i + 1 until pts.size) {
                val b = pts[j]

                val area = a.getRectangleAreaTo(b)
//                println("a: $a | b: $b | area: $area | bestArea: $bestArea")

                if (area <= bestArea) continue
                if (rectangleIntersectsPolygon(a, b, pts)) continue

                bestArea = area
            }
        }

        return bestArea
    }
}

// -------- manual runner --------

fun main() =
    Day09.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
