import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    data class Point(val x: Int, val y: Int, val z: Int)
    data class Brick(val from: Point, val to: Point) {
        private val dx = (to.x - from.x).sign
        private val dy = (to.y - from.y).sign
        private val length = abs(to.x - from.x) + abs(to.y - from.y)

        fun forEachBrickCube(func: (Int, Int) -> Unit) {
            for (i in 0..length) {
                val x = from.x + i * dx
                val y = from.y + i * dy
                func(x, y)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val bricks = mutableListOf<Brick>()
        for (line in input) {
            val (from, to) = line.split("~")
            val (x1, y1, z1) = from.split(",").map { it.toInt() }
            val (x2, y2, z2) = to.split(",").map { it.toInt() }
            bricks.add(Brick(Point(x1, y1, z1), Point(x2, y2, z2)))
        }
        bricks.sortBy { it.from.z }
        val result = BooleanArray(bricks.size) { true }
        val maxZs = Array(10) { IntArray(10 ) }
        val brickIndexes = Array(10) { IntArray(10 ) { -1 } }
        for ((i, brick) in bricks.withIndex()) {
            var maxZ = 0
            brick.forEachBrickCube { x, y ->
                maxZ = max(maxZ, maxZs[x][y])
            }
            val bricksBelow = hashSetOf<Int>()
            brick.forEachBrickCube { x, y ->
                if (maxZ == maxZs[x][y] && brickIndexes[x][y] != -1)
                    bricksBelow.add(brickIndexes[x][y])
            }
            if (bricksBelow.size == 1) result[bricksBelow.single()] = false
            val newZ = maxZ + 1 + brick.to.z - brick.from.z
            brick.forEachBrickCube { x, y ->
                maxZs[x][y] = newZ
                brickIndexes[x][y] = i
            }
        }
        return result.count { it }
    }

    fun part2(input: List<String>): Int {
        val bricks = mutableListOf<Brick>()
        for (line in input) {
            val (from, to) = line.split("~")
            val (x1, y1, z1) = from.split(",").map { it.toInt() }
            val (x2, y2, z2) = to.split(",").map { it.toInt() }
            bricks.add(Brick(Point(x1, y1, z1), Point(x2, y2, z2)))
        }
        bricks.sortBy { it.from.z }
        val maxZs = Array(10) { IntArray(10 ) }
        val brickIndexes = Array(10) { IntArray(10 ) { -1 } }
        val bricksBelow = Array(bricks.size) { hashSetOf<Int>() }
        for ((i, brick) in bricks.withIndex()) {
            var maxZ = 0
            brick.forEachBrickCube { x, y ->
                maxZ = max(maxZ, maxZs[x][y])
            }
            brick.forEachBrickCube { x, y ->
                if (maxZ == maxZs[x][y])
                    bricksBelow[i].add(brickIndexes[x][y])
            }
            val newZ = maxZ + 1 + brick.to.z - brick.from.z
            brick.forEachBrickCube { x, y ->
                maxZs[x][y] = newZ
                brickIndexes[x][y] = i
            }
        }
        var ans = 0
        for (i in bricks.indices) {
            val toRemove = hashSetOf<Int>()
            toRemove.add(i)
            do {
                var falling = false
                for (j in bricks.indices) {
                    if (j !in toRemove && (bricksBelow[j].isEmpty() ||  bricksBelow[j].intersect(toRemove) == bricksBelow[j])) {
                        toRemove += j
                        falling = true
                    }
                }
            } while (falling)
            ans += toRemove.size - 1
        }
        return ans
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 5)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 7)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}