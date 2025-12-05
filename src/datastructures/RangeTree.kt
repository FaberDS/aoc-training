package datastructures

import jdk.internal.vm.ThreadContainers.root
import javax.swing.tree.TreeNode

class RangeNode(var min: Long, var max: Long, var minNode: RangeNode? = null, var maxNode: RangeNode?= null) {

    var value: Long = max - min

    override fun toString(): String {
        return "RN |min: $min,  max: $max, value: $value |  \n -> minNode: $minNode, \n -> maxNode: $maxNode"
    }
    fun sumValidIds(): Long {
        var result = 0L
        val stack = ArrayDeque<RangeNode>()
        var current: RangeNode? = this

        while (current != null || stack.isNotEmpty()) {
            while (current != null) {
                stack.add(current)
                current = current.minNode
                val maxNode = current?.maxNode
                if (maxNode != null) {
                    stack.add(maxNode)
                }
            }
            current = stack.removeLast()
            result += current.value
            current = current.maxNode
        }
        return result
    }
    fun insertNode(node: RangeNode) {
        val relation = when {
            node.min > min && node.max < max -> "INSIDE (no-op)"
            node.max < min                   -> "LEFT (go to minNode)"
            node.min > max                   -> "RIGHT (go to maxNode)"
            node.min > min                   -> "EXTEND_MAX (max -> ${node.max})"
            node.min < min                   -> "EXTEND_MIN (min -> ${node.min})"
            else                             -> "UNKNOWN"
        }

        println(
            "insertNode: inserting [${node.min}, ${node.max}] " +
                    "into [${this.min}, ${this.max}] " +
                    "relation=$relation " +
                    "minNode=${minNode?.let { "[${it.min}, ${it.max}]" } ?: "null"} " +
                    "maxNode=${maxNode?.let { "[${it.min}, ${it.max}]" } ?: "null"}"
        )
        if(node.min > min && node.max < max) return // is in RANGE
        if(node.max < min) {
            if(minNode == null) {
                minNode = node
                return
            }
            minNode!!.insertNode(node)
        } else if (node.min < max) {
            // FIND MAX value of child and clear childs
        }
        else if (node.min > max) {
            if(maxNode == null) {
                maxNode = node
                return
            }
            maxNode!!.insertNode(node)
        } else if (node.min > min){
            max = node.max
        } else if(node.min < min) {
            min = node.min
        }
    }
}
