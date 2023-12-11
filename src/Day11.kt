import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Point(val x: Int, val y: Int)

    fun solve(input: List<String>, toAdd: Long): Long {
        val emptyLinesIndices = input.indices.filter { i -> input[i].all { it == '.' } }.toSet()
        val emptyColumnsIndices = input[0].indices.filter { j -> input.map { it[j] }.all { it == '.' } }.toSet()
        val points = mutableListOf<Point>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') points.add(Point(i, j))
            }
        }
        var ans = 0L
        for (i in 0..points.size - 2) {
            for (j in i + 1..<points.size) {
                for (x in min(points[i].x, points[j].x) + 1..max(points[i].x, points[j].x)) {
                    if (emptyLinesIndices.contains(x)) ans += (toAdd - 1)
                    ans++
                }
                for (y in min(points[i].y, points[j].y) + 1..max(points[i].y, points[j].y)) {
                    if (emptyColumnsIndices.contains(y)) ans += (toAdd - 1)
                    ans++
                }
            }
        }
        return ans
    }

    val testInput1 = readInput("test_1")
    check(solve(testInput1, 2) == 374L)
    val testInput2 = readInput("test_2")
    check(solve(testInput2, 10) == 1030L)
    check(solve(testInput2, 100) == 8410L)

    val input = readInput("input")
    solve(input, 2).println()
    solve(input, 1000000).println()
}