class RangeNode(
    var min: Long,
    var max: Long,
    var minNode: RangeNode? = null,
    var maxNode: RangeNode? = null
) {

    var value: Long = max - min
        private set

    private fun updateValue() {
        value = (max - min)
    }

    override fun toString(): String {
        return "RN |min: $min,  max: $max, value: $value |  \n -> minNode: $minNode, \n -> maxNode: $maxNode"
    }

    fun sumValidIds(): Long =
        (value + 1) +
                (minNode?.sumValidIds() ?: 0L) +
                (maxNode?.sumValidIds() ?: 0L)
    private enum class Relation {
        INSIDE,
        LEFT,
        RIGHT,
        OVERLAP
    }

    private fun relationTo(node: RangeNode): Relation =
        when {
            node.min >= min && node.max <= max -> Relation.INSIDE
            node.max < min                     -> Relation.LEFT
            node.min > max                     -> Relation.RIGHT
            else                               -> Relation.OVERLAP
        }

    fun insertNode(node: RangeNode) {
        when (relationTo(node)) {
            Relation.INSIDE -> {
                return
            }

            Relation.LEFT -> {
                if (minNode == null) {
                    minNode = node
                } else {
                    minNode!!.insertNode(node)
                }
            }

            Relation.RIGHT -> {
                if (maxNode == null) {
                    maxNode = node
                } else {
                    maxNode!!.insertNode(node)
                }
            }

            Relation.OVERLAP -> {
                extendAndReinsert(node)
            }
        }
    }

    private fun extendAndReinsert(node: RangeNode) {
        if (node.min < min) {
            min = node.min
        }
        if (node.max > max) {
            max = node.max
        }
        updateValue()

        val oldLeft = minNode
        val oldRight = maxNode
        minNode = null
        maxNode = null

        if (oldLeft != null) {
            reinsertSubtree(oldLeft)
        }

        if (oldRight != null) {
            reinsertSubtree(oldRight)
        }
    }
    private fun reinsertSubtree(node: RangeNode) {
        val left = node.minNode
        val right = node.maxNode

        node.minNode = null
        node.maxNode = null

        insertNode(node)

        if (left != null) reinsertSubtree(left)
        if (right != null) reinsertSubtree(right)
    }

    fun printTree(prefix: String = "", isLeft: Boolean = true) {
        maxNode?.printTree(prefix + if (isLeft) "│   " else "    ", false)

        val connector = if (isLeft) "└── " else "┌── "
        println(prefix + connector + "[$min, $max] v=$value")

        minNode?.printTree(prefix + if (isLeft) "    " else "│   ", true)
    }

}
