package datastructures

import java.awt.Rectangle
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

fun List<String>.toPoints(): List<Point> =
    this.map { line ->
        val (x, y) = line
            .split(',')
            .filter(String::isNotBlank)
            .map(String::toInt)

        Point(x, y)
    }

fun Point.getRectangleAreaTo(pt: Point): Long {
    val width  = abs(this.x - pt.x) + 1
    val height = abs(this.y - pt.y) + 1
    return width.toLong() * height.toLong()
}