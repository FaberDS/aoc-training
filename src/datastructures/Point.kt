package datastructures

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun isInSameRow(p: Point) = x == p.x
    fun isInSameColumn(p: Point) = y == p.y
}


fun List<String>.toPoints(): List<Point> =
    this.map { line ->
        val (x, y) = line
            .split(',')
            .filter(String::isNotBlank)
            .map(String::toInt)

        Point(x, y)
    }
fun Point.getRectangleAreaTo(pt: Point): Long {
    val width  = abs(x - pt.x) + 1
    val height = abs(y - pt.y) + 1
    return width.toLong() * height.toLong()
}
