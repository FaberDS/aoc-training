package utils
import com.microsoft.z3.ArithExpr
import com.microsoft.z3.ArithSort
import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status

data class Machine(
    val targets: IntArray,
    val buttons: List<List<Int>>
)

fun minPressesForMachine(machine: Machine): Int {
    val cfg = hashMapOf("model" to "true")

    Context(cfg).use { ctx ->
        val numButtons = machine.buttons.size
        val numCounters = machine.targets.size

        val opt = ctx.mkOptimize()

        val x: Array<IntExpr> = Array(numButtons) { j ->
            ctx.mkIntConst("x$j") as IntExpr
        }

        for (j in 0 until numButtons) {
            opt.Add(ctx.mkGe(x[j], ctx.mkInt(0)))
        }

        // For each counter i: sum_j (button j affects i) * x_j = targets[i]
        for (i in 0 until numCounters) {
            var sum: ArithExpr<out ArithSort> = ctx.mkInt(0)

            for (j in 0 until numButtons) {
                if (machine.buttons[j].contains(i)) {
                    sum = ctx.mkAdd(sum, x[j]) as ArithExpr<out ArithSort>
                }
            }

            val target = ctx.mkInt(machine.targets[i])
            opt.Add(ctx.mkEq(sum, target))
        }

        // Minimize total presses sum_j x_j
        val xAsArith: Array<ArithExpr<out ArithSort>> =
            x.map { it as ArithExpr<out ArithSort> }.toTypedArray()

        val totalPresses: ArithExpr<out ArithSort> =
            ctx.mkAdd(*xAsArith) as ArithExpr<out ArithSort>

        opt.MkMinimize(totalPresses)

        val status = opt.Check()
        if (status != Status.SATISFIABLE) {
            error("Machine is unsatisfiable or unknown: $status")
        }

        val model = opt.model

        var result = 0
        for (j in 0 until numButtons) {
            val value = model.eval(x[j], false) as IntNum
            result += value.int
        }

        return result
    }
}
