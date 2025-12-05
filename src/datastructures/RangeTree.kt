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

    fun insertNode(node: RangeNode) {
        val baseString =
            "insertNode: [${node.min}, ${node.max}]  +\n into [${this.min}, ${this.max}] with: minNode == null ${minNode == null} |maxNode == null ${maxNode == null}"

        if (node.min >= min && node.max <= max) {
//            println("$baseString \n -> INSIDE (no-op)")
            return
        }

        else if (node.max < min) {
//            println("$baseString \n -> LEFT (go to minNode)")
            if (minNode == null) {
                minNode = node
            } else {
                minNode!!.insertNode(node)
            }
            return
        }

        else if (node.min > max) {
//            println("$baseString \n -> RIGHT (go to maxNode)")
            if (maxNode == null) {
                maxNode = node
            } else {
                maxNode!!.insertNode(node)
            }
            return
        }

//        println("$baseString \n -> OVERLAP (EXTEND / MERGE)")

        val oldMin = min
        val oldMax = max

        if (node.min < min) {
            min = node.min
//            println("$baseString \n -> EXTEND_MIN ($oldMin -> $min)")
        }
        if (node.max > max) {
            max = node.max
//            println("$baseString \n -> EXTEND_MAX ($oldMax -> $max)")
        }
        updateValue()

        val oldLeft = minNode
        val oldRight = maxNode
        minNode = null
        maxNode = null

        if (oldLeft != null) {
//            println("$baseString \n -> REINSERT LEFT SUBTREE")
            reinsertSubtree(oldLeft)
        }

        if (oldRight != null) {
//            println("$baseString \n -> REINSERT RIGHT SUBTREE")
            reinsertSubtree(oldRight)
        }
    }
    private fun reinsertSubtree(node: RangeNode) {
        val left = node.minNode
        val right = node.maxNode

        node.minNode = null
        node.maxNode = null

//        println("reinsertSubtree: reinserting [${node.min}, ${node.max}] into root [${this.min}, ${this.max}]")
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
