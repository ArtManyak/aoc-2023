fun main() {

    fun part1(input: List<String>, from: Double, to: Double): Long {
        var ans = 0L
        for (i in input.indices) {
            for (j in i+1..<input.size) {
                // y = kx + b -> k = Vy / Vx; b = Y0 - kX0
                val (xa, ya, _) = input[i].split(" @ ")[0].split(", ").map { it.toDouble() }
                val (vxa, vya, _) = input[i].split(" @ ")[1].split(", ").map { it.toDouble() }
                val (xb, yb, _) = input[j].split(" @ ")[0].split(", ").map { it.toDouble() }
                val (vxb, vyb, _) = input[j].split(" @ ")[1].split(", ").map { it.toDouble() }
                val ka = vya / vxa
                val ba = ya - ka * xa
                val kb = vyb / vxb
                val bb = yb - kb * xb
                val intersectX = (bb - ba) / (ka - kb)
                val intersectY = ka * intersectX + ba
                val inRange = intersectX in (from..to) && intersectY in (from..to)
                val possibleForXA = (vxa > 0 && intersectX >= xa) || (vxa < 0 && intersectX <= xa)
                val possibleForYA = (vya > 0 && intersectY >= ya) || (vya < 0 && intersectY <= ya)
                val possibleForXB = (vxb > 0 && intersectX >= xb) || (vxb < 0 && intersectX <= xb)
                val possibleForYB = (vyb > 0 && intersectY >= yb) || (vyb < 0 && intersectY <= yb)
                val possibleForA = possibleForXA && possibleForYA
                val possibleForB = possibleForXB && possibleForYB
                if (inRange && possibleForA && possibleForB) ans++
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1, 7.0, 27.0) == 2L)
//    val testInput2 = readInput("test_2")
//    check(part2(testInput2) == 154)

    val input = readInput("input")
    part1(input, 200000000000000.0, 400000000000000.0).println()
//    part2(input).println()
}