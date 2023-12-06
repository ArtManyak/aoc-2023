import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    fun solve(time: Long, distance: Long): Long {
        val sqrt = sqrt((time * time - 4 * distance).toDouble())
        val x1 = floor((time - sqrt) / 2 + 1).toLong()
        val x2 = ceil((time + sqrt) / 2 - 1).toLong()
        return x2 - x1 + 1
    }

    fun part1(input: List<String>): Long {
        val times = input[0].substringAfter(":").trim().split("\\s++".toRegex()).map { it.toLong() }
        val distances = input[1].substringAfter(":").trim().split("\\s++".toRegex()).map { it.toLong() }
        var totalRes = 1L
        for (i in times.indices) {
            totalRes *= solve(times[i], distances[i])
        }
        return totalRes
    }

    fun part2(input: List<String>): Long {
        val time = input[0].substringAfter(":").replace("\\s+".toRegex(), "").toLong()
        val distance = input[1].substringAfter(":").replace("\\s+".toRegex(), "").toLong()
        return solve(time, distance)
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 288L)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 71503L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
