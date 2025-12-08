package datastructures
data class Coord3D(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Coord3D): Long {
        val (x1, y1, z1) = this
        val (x2, y2, z2) = other
        val deltaX = (x1 - x2).toLong()
        val deltaY = (y1 - y2).toLong()
        val deltaZ = (z1 - z2).toLong()
        return deltaX + deltaY + deltaZ
    }
    fun distanceToSquare(other: Coord3D): Long {
        val (x1, y1, z1) = this
        val (x2, y2, z2) = other
        val dx = (x1 - x2).toLong()
        val dy = (y1 - y2).toLong()
        val dz = (z1 - z2).toLong()
        return dx * dx + dy * dy + dz * dz
    }
}

fun List<String>.splitInputToCoord3List() =
    this.map { line ->
        val (x,y,z) = line
            .split(',')
            .filter(String::isNotBlank)
            .map(String::toInt)
        Coord3D(x, y, z)
    }
data class Edge(val a: Coord3D, val b: Coord3D, val dist2: Long)
