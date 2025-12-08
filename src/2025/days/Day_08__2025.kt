/**
 * Advent of code (2025) solution for day 8 by Denis Schüle.
 * https://adventofcode.com/2025/day/8
 */
import aoc.AoCDay
import datastructures.Coord3D
import datastructures.DSU
import datastructures.Edge
import datastructures.createEdgesForCoords
import datastructures.indexed
import datastructures.splitInputToCoord3List

/**
 *   PART-1
 *   The Elves are trying to figure out which junction boxes to connect so that electricity can reach every junction box. They even have a list of all of the junction boxes' positions in 3D space (your puzzle input).
 *   This list describes the position of 20 junction boxes, one per line. Each position is given as X,Y,Z coordinates. So, the first junction box in the list is at X=162, Y=817, Z=812.
 *
 * To save on string lights, the Elves would like to focus on connecting pairs of junction boxes that are as close together as possible according to straight-line distance. In this example, the two junction boxes which are closest together are 162,817,812 and 425,690,689.
 *
 * By connecting these two junction boxes together, because electricity can flow between them, they become part of the same circuit. After connecting them, there is a single circuit which contains two junction boxes, and the remaining 18 junction boxes remain in their own individual circuits.
 *
 * Now, the two junction boxes which are closest together but aren't already directly connected are 162,817,812 and 431,825,988. After connecting them, since 162,817,812 is already connected to another junction box, there is now a single circuit which contains three junction boxes and an additional 17 circuits which contain one junction box each.
 *
 * The next two junction boxes to connect are 906,360,560 and 805,96,715. After connecting them, there is a circuit containing 3 junction boxes, a circuit containing 2 junction boxes, and 15 circuits which contain one junction box each.
 *
 * The next two junction boxes are 431,825,988 and 425,690,689. Because these two junction boxes were already in the same circuit, nothing happens!
 *
 * This process continues for a while, and the Elves are concerned that they don't have enough extension cables for all these circuits. They would like to know how big the circuits will be.
 *
 * After making the ten shortest connections, there are 11 circuits: one circuit which contains 5 junction boxes, one circuit which contains 4 junction boxes, two circuits which contain 2 junction boxes each, and seven circuits which each contain a single junction box. Multiplying together the sizes of the three largest circuits (5, 4, and one of the circuits of size 2) produces 40.
 *
 * Your list contains many junction boxes; connect together the 1000 pairs of junction boxes which are closest together. Afterward, what do you get if you multiply together the sizes of the three largest circuits?
 *
 *   PART-2
 * The Elves were right; they definitely don't have enough extension cables.
 * You'll need to keep connecting junction boxes together until they're all in one large circuit.
 *
 * Continuing the above example, the first connection which causes all of the junction boxes
 * to form a single circuit is between the junction boxes at 216,146,977 and 117,168,530.
 * The Elves need to know how far those junction boxes are from the wall so they can pick the right extension cable;
 * multiplying the X coordinates of those two junction boxes (216 and 117) produces 25272.
 *
 * Continue connecting the closest unconnected pairs of junction boxes together until they're all in the same circuit.
 * What do you get if you multiply together the X coordinates of the last two junction boxes you need to connect?
 *
 */

object Day08 : AoCDay<Long>(
    year = 2025,
    day = 8,
    example1Expected = 40,
    example2Expected = 25272,
    answer1Expected = 54180,
    answer2Expected = 25325968,
) {
    override fun part1(input: List<String>): Long {
        val coords = input.splitInputToCoord3List()

        val pairsToConnect = if (coords.size == 20) 10 else 1000

        val indexOf: Map<Coord3D, Int> = coords.indexed()

        val edges = coords.createEdgesForCoords()

        val sortedEdges = edges.sortedBy { it.dist2 }

        val dsu = DSU(coords.size)

        for (edge in sortedEdges.take(pairsToConnect)) {
            val ia = indexOf[edge.a]!!
            val ib = indexOf[edge.b]!!
            dsu.union(ia, ib)
        }

        val sizesDesc = dsu.componentSizes().sortedDescending()

        val answer = sizesDesc
            .take(3)
            .map { it.toLong() }
            .reduce { acc, size -> acc * size }

        return answer
    }

    override fun part2(input: List<String>): Long {
        val coords = input.splitInputToCoord3List()
        val n = coords.size

        val indexOf: Map<Coord3D, Int> = coords.indexed()

        val edges = coords.createEdgesForCoords()

        val sortedEdges = edges.sortedBy { it.dist2 }

        val dsu = DSU(n)
        var components = n
        var lastEdge: Edge? = null

        for (e in sortedEdges) {
            val u = indexOf[e.a]!!
            val v = indexOf[e.b]!!

            if (dsu.union(u, v)) {
                components--
                lastEdge = e
                if (components == 1) break
            }
        }

        val le = lastEdge ?: error("Graph never became connected")
        val x1 = le.a.x.toLong()
        val x2 = le.b.x.toLong()

        return x1 * x2
    }
}

// -------- manual runner --------

fun main() =
    Day08.run(
        runExamples = true,
        runReal = true,
        runPart1 = true,
        runPart2 = true,
        submit1 = false,
        submit2 = false,
    )
