import java.util.LinkedList
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>, maxSteps: Int): Long {
        val was = mutableSetOf<Pair<Int, Int>>()
        val queue = LinkedList<Pair<Int, Int>>()
        var accessible = 0L
        for (i in input.indices) {
            val j = input[i].indexOf('S')
            if (j != -1) {
                queue.add(Pair(i, j))
                break
            }
        }

        was.add(queue.peek())
        accessible++
        for (step in 1..maxSteps) {
            val toAdd = mutableSetOf<Pair<Int, Int>>()
            while (queue.isNotEmpty()) {
                val point = queue.remove()
                for (dx in -1..1) {
                    if (point.first + dx < 0 || point.first + dx >= input.size) continue
                    for (dy in -1..1) {
                        if (abs(dx) + abs(dy) == 2) continue
                        if (point.second + dy < 0 || point.second + dy >= input[0].length) continue
                        val newPoint = Pair(point.first + dx, point.second + dy)
                        if (was.contains(newPoint) || input[newPoint.first][newPoint.second] == '#') continue
                        toAdd.add(newPoint)
                        was.add(newPoint)
                        if (step % 2 == 0) accessible++
                    }
                }
            }
            queue.addAll(toAdd)
        }
        return accessible
    }

    fun part2(input: List<String>, maxSteps: Int): Long {
        val was = mutableSetOf<Pair<Int, Int>>()
        val queue = LinkedList<Pair<Int, Int>>()
        var accessible = 0L
        for (i in input.indices) {
            val j = input[i].indexOf('S')
            if (j != -1) {
                queue.add(Pair(i, j))
                break
            }
        }

        was.add(queue.peek())
        accessible++
        for (step in 1..maxSteps) {
            val toAdd = mutableSetOf<Pair<Int, Int>>()
            while (queue.isNotEmpty()) {
                val point = queue.remove()
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        if (abs(dx) + abs(dy) == 2) continue
                        val newPoint = Pair(point.first + dx, point.second + dy)
                        var newX = newPoint.first
                        var newY = newPoint.second
                        while (newX < 0) newX += input.size
                        while (newY < 0) newY += input[0].length
                        if (was.contains(newPoint) || input[newX%input.size][newY%input[0].length] == '#') continue
                        toAdd.add(newPoint)
                        was.add(newPoint)
                        if (step % 2 == maxSteps % 2) accessible++
                    }
                }
            }
            queue.addAll(toAdd)
        }
        return if (maxSteps % 2 == 0 ) accessible else accessible - 1
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1, 6) == 16L)
    val testInput2 = readInput("test_2")
    check(part2(testInput1, 1) == 2L)
    check(part2(testInput1, 2) == 4L)
    check(part2(testInput1, 3) == 6L)
    check(part2(testInput2, 6) == 16L)
    check(part2(testInput2, 10) == 50L)
    check(part2(testInput2, 50) == 1594L)
    check(part2(testInput2, 100) == 6536L)
    check(part2(testInput2, 500) == 167004L)
    check(part2(testInput2, 1000) == 668697L)
    check(part2(testInput2, 5000) == 16733044L)

    val input = readInput("input")
    part1(input, 64).println()
    part2(input, 26501365).println()
}