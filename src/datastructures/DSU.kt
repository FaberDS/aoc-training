package datastructures

// Disjoint Set Union (Union–Find)
// https://www.geeksforgeeks.org/dsa/introduction-to-disjoint-set-data-structure-or-union-find-algorithm/
class DSU(n: Int) {
    private val parent = IntArray(n) { it }
    private val size   = IntArray(n) { 1 }

    private fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x])
        }
        return parent[x]
    }

    fun union(a: Int, b: Int): Boolean {
        var ra = find(a)
        var rb = find(b)
        if (ra == rb) return false

        if (size[ra] < size[rb]) {
            val tmp = ra
            ra = rb
            rb = tmp
        }
        parent[rb] = ra
        size[ra] += size[rb]
        return true
    }

    fun componentSizes(): List<Int> {
        val counts = mutableMapOf<Int, Int>()
        for (i in parent.indices) {
            val root = find(i)
            counts[root] = (counts[root] ?: 0) + 1
        }
        return counts.values.toList()
    }
}