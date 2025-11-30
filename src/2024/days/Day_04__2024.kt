/**
 * Advent of code (2024) solution for day 4 by Denis Schüle.
 * [Advent of code 2024-4 ](https://adventofcode.com/2024/day/4)
 *
 * .M.S......
 * ..A..MSMS.
 * .M.S.MAA..
 * ..A.ASMSM.
 * .M.S.M....
 * ..........
 * S.S.S.S.S.
 * .A.A.A.A..
 * M.M.M.M.M.
 * ..........
 **/
 import extensions.printSeparated
 import extensions.safeGet
 import kotlin.Char
 import kotlin.time.measureTimedValue


fun main() {

    val exampleSolution1 = 18
    val exampleSolution2 = 9
    /**
    * Helper Part 1
     */
    fun testXmasHorizontal(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row,a.col+1) == 'M' &&
                grid.safeGet(a.row,a.col+2) == 'A' &&
                grid.safeGet(a.row,a.col+3) == 'S'
    }
    fun testXmasHorizontalBackward(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row,a.col-1) == 'M' &&
                grid.safeGet(a.row,a.col-2) == 'A' &&
                grid.safeGet(a.row,a.col-3) == 'S'
    }
    fun testXmasVertical(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row+1,a.col) == 'M' &&
                grid.safeGet(a.row+2,a.col) == 'A' &&
                grid.safeGet(a.row+3,a.col) == 'S'
    }
    fun testXmasVerticalBackward(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row-1,a.col) == 'M' &&
                grid.safeGet(a.row-2,a.col) == 'A' &&
                grid.safeGet(a.row-3,a.col) == 'S'
    }
    fun testXmasDiagonalLeftUp(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row-1,a.col-1) == 'M' &&
                grid.safeGet(a.row-2,a.col-2) == 'A' &&
                grid.safeGet(a.row-3,a.col-3) == 'S'
    }
    fun testXmasDiagonalLeftDown(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row+1,a.col-1) == 'M' &&
                grid.safeGet(a.row+2,a.col-2) == 'A' &&
                grid.safeGet(a.row+3,a.col-3) == 'S'
    }
    fun testXmasDiagonalRightDown(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row+1,a.col+1) == 'M' &&
                grid.safeGet(a.row+2,a.col+2) == 'A' &&
                grid.safeGet(a.row+3,a.col+3) == 'S'
    }
    fun testXmasDiagonalRightUp(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row,a.col) == 'X' &&
                grid.safeGet(a.row-1,a.col+1) == 'M' &&
                grid.safeGet(a.row-2,a.col+2) == 'A' &&
                grid.safeGet(a.row-3,a.col+3) == 'S'
    }
    fun matchXmas(grid: List<List<Char>>, m:Coord,a:Coord,s:Coord): Boolean {
        return grid[m.row][m.col] == 'M' && grid[a.row][a.col] == 'A' && grid[s.row][s.col] == 'S'
    }

    /**
     * Helper Part 2
     */
    /** right to left
     * M.S
     * .A.
     * M.S
     */
    fun testMas(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row-1,a.col-1) == 'M' &&
                grid.safeGet(a.row+1,a.col-1) == 'M' &&
                grid.safeGet(a.row-1,a.col+1) == 'S' &&
                grid.safeGet(a.row+1,a.col+1) == 'S'

    }
    /** right to left
     * M.M
     * .A.
     * S.S
     */
    fun testMas90Degree(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row-1,a.col-1) == 'M' &&
                grid.safeGet(a.row+1,a.col-1) == 'S' &&
                grid.safeGet(a.row-1,a.col+1) == 'M' &&
                grid.safeGet(a.row+1,a.col+1) == 'S'

    }
    /** right to left
     * S.S
     * .A.
     * M.M
     */
    fun testMas270Degree(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row-1,a.col-1) == 'S' &&
                grid.safeGet(a.row+1,a.col-1) == 'M' &&
                grid.safeGet(a.row-1,a.col+1) == 'S' &&
                grid.safeGet(a.row+1,a.col+1) == 'M'

    }

    /** left to right
     * S.M
     * .A.
     * S.M
     */
    fun testSam(grid: List<List<Char>>, a:Coord): Boolean {
        return grid.safeGet(a.row-1,a.col-1)== 'S' &&
                grid.safeGet(a.row+1,a.col-1) == 'S' &&
                grid.safeGet(a.row-1,a.col+1) == 'M' &&
                grid.safeGet(a.row+1,a.col+1) == 'M'
    }

    /**************************************
     * Part 1
     **************************************/
    fun part1(input: List<String>): Int {
        var count = 0
        val grid = splitIntoCharGrid(input)
        val coords = findCoordsForChar('X',grid)

        val height = grid.size
        val width = grid.first().size
        for(coord in coords){
            val row = coord.row
            val col = coord.col
            if(row >= 3) {
                // investigate up
                if(matchXmas(grid.toList(), Coord(row-1,col),Coord(row-2,col),Coord(row-3,col))) count++
                if(col >= 3){
                    // investigate left up diagonal
                    if(matchXmas(grid.toList(), Coord(row-1,col-1),Coord(row-2,col-2),Coord(row-3,col-3))) count++
                }
                if(col < width -3){
                    // investigate right up diagonal
                    if(matchXmas(grid.toList(), Coord(row-1,col+1),Coord(row-2,col+2),Coord(row-3,col+3))) count++
                }
            }
            if(row < height -3){
                // investigate down
                if(matchXmas(grid.toList(), Coord(row+1,col),Coord(row+2,col),Coord(row+3,col))) count++

                if(col >= 3){
                    // investigate left down diagonal
                    if(matchXmas(grid.toList(), Coord(row+1,col-1),Coord(row+2,col-2),Coord(row+3,col-3))) count++
                }
                if(col < width -3){
                    // investigate right down diagonal
                    if(matchXmas(grid.toList(), Coord(row+1,col+1),Coord(row+2,col+2),Coord(row+3,col+3))) count++
                }
            }
            if(col < width -3){
                // investigate right
                if(matchXmas(grid.toList(), Coord(row,col+1),Coord(row,col+2),Coord(row,col+3))) count++

            }
            if(col >= 3){
                // investigate left
                if(matchXmas(grid.toList(), Coord(row,col-1),Coord(row,col-2),Coord(row,col-3))) count++
            }
        }
        return count
    }
    /**************************************
     * Part 1 Refactored
     **************************************/
    fun part1Ref(input: List<String>): Int {
        var count = 0
        val grid = splitIntoCharGrid(input)
        val coords = findCoordsForChar('X', grid)

        for (coord in coords) {
            if(testXmasVertical(grid, coord)) count++
            if (testXmasDiagonalLeftUp(grid, coord)) count++
            if (testXmasDiagonalLeftDown(grid, coord)) count++
            if (testXmasDiagonalRightDown(grid, coord)) count++
            if (testXmasDiagonalRightUp(grid, coord)) count++
            if (testXmasVerticalBackward(grid, coord)) count++
            if (testXmasHorizontalBackward(grid, coord)) count++
            if (testXmasHorizontal(grid, coord)) count++
        }
        return count
    }
    /**************************************
     * Part 2
     **************************************/

    fun part2(input: List<String>): Int {
        var count = 0
        val grid = splitIntoCharGrid(input)
        val coords = findCoordsForChar('A',grid)
        for(coord in coords){

            if(testMas(grid,coord)) count++

            if(testSam(grid,coord)) count++

            if(testMas270Degree(grid,coord)) count++

            if(testMas90Degree(grid,coord)) count++

        }
        return count
    }

    /**************************************
     * Part 2 refactored
     **************************************/
    fun part2Ref(input: List<String>): Int {
        val grid = splitIntoCharGrid(input)
        val coords = findCoordsForChar('A',grid)
        var patternTests = listOf(::testMas,::testSam,::testMas270Degree,::testMas90Degree)
        val count = coords.sumOf { coord ->
            patternTests.count { testFunction ->
                val result = testFunction(grid, coord)
                result
            }
        }
        return count
    }

    try {
    
        /* --- TEST DEMO INPUT --- */
        val exampleInput = readInput("day_04_demo", "2024")
        val part1_demo_solution = part1(exampleInput)
        "Part 1 Demo".printSeparated()
        println("- Part 1 Demo: ${part1_demo_solution}") 
        check(part1_demo_solution == exampleSolution1)
        
        
        "Part 2 Demo".printSeparated()
        val exampleInput2 = readInput("day_04_demo_2", "2024")

        val part2_ref_demo_solution = part2Ref(exampleInput2)
        println("- Part 2_REF Demo: ${part2_ref_demo_solution}")
        val part2_demo_solution = part2Ref(exampleInput2)
        println("- Part 2 Demo: ${part2_demo_solution}")
        check(part2_demo_solution == exampleSolution2)

        /* --- RUN FULL INPUT ---
            Reads input from the file src/2024/input/day_04.txt
            NOTE: You need to implement or import the readInput function. */


        "Part 1".printSeparated()

        val (part1_solution, timeTakenPart1) = measureTimedValue {
            val input = readInput("day_04", "2024")
            part1(input)
        }
        println("- Part 1: ${part1_solution} in $timeTakenPart1")
         val (part1_ref_solution, timeTakenPart1_ref) = measureTimedValue {
                    val input = readInput("day_04", "2024")
                    part1Ref(input)
        }
        println("- Part 1 ref: ${part1_ref_solution} in $timeTakenPart1_ref")


        check(part1_solution == 2493)
        check(part1_ref_solution == 2493)

        val (part2_solution, timeTakenPart2) = measureTimedValue {
            val input = readInput("day_04", "2024")
            part2(input)
        }
        val (part2_ref_solution, timeTakenPart2_ref) = measureTimedValue {
            val input = readInput("day_04", "2024")
            part2Ref(input)
        }
        "Part 2".printSeparated()
        println("- Part 2: ${part2_solution} in $timeTakenPart2")
        println("- Part 2_ref: ${part2_ref_solution} in $part2_ref_solution")
        check(part2_solution == 1890)

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}