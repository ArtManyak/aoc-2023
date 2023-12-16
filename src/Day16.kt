import Direction.*
import kotlin.math.max

enum class Direction {
    Left, Right, Up, Down
}
fun main() {
    data class Beam(var x: Int, var y: Int, var direction: Direction)
    fun Beam.move(maxX: Int, maxY: Int): Beam? {
        return when (direction) {
            Left -> if (y == 0) null else Beam(x, y - 1, Left)
            Right -> if (y == maxY) null else Beam(x, y + 1, Right)
            Up -> if (x == 0) null else Beam(x - 1, y, Up)
            Down -> if (x == maxX) null else Beam(x + 1, y, Down)
        }
    }

    fun solve(input: List<String>, beams: MutableList<Beam>): Int {
        val energized = List(input.size) { MutableList(input[0].length) { false } }
        val maxX = input.size - 1
        val maxY = input[0].length - 1
        val set = mutableSetOf<String>()
        while (beams.isNotEmpty()) {
            val beam = beams.removeFirst()
            energized[beam.x][beam.y] = true
            if (!set.add("${beam.x};${beam.y};${beam.direction}")) continue
            when (input[beam.x][beam.y]) {
                '.' -> beam.move(maxX, maxY)?.let { beams.add(it) }
                '/' -> {
                    when (beam.direction) {
                        Right -> beam.direction = Up
                        Up -> beam.direction = Right
                        Left -> beam.direction = Down
                        Down -> beam.direction = Left
                    }
                    beam.move(maxX, maxY)?.let { beams.add(it) }
                }

                '\\' -> {
                    when (beam.direction) {
                        Right -> beam.direction = Down
                        Down -> beam.direction = Right
                        Left -> beam.direction = Up
                        Up -> beam.direction = Left
                    }
                    beam.move(maxX, maxY)?.let { beams.add(it) }
                }

                '-' -> {
                    if (beam.direction == Left || beam.direction == Right) {
                        beam.move(maxX, maxY)?.let { beams.add(it) }
                    } else {
                        Beam(beam.x, beam.y, Left).move(maxX, maxY)?.let { beams.add(it) }
                        Beam(beam.x, beam.y, Right).move(maxX, maxY)?.let { beams.add(it) }
                    }
                }

                '|' -> {
                    if (beam.direction == Up || beam.direction == Down) {
                        beam.move(maxX, maxY)?.let { beams.add(it) }
                    } else {
                        Beam(beam.x, beam.y, Up).move(maxX, maxY)?.let { beams.add(it) }
                        Beam(beam.x, beam.y, Down).move(maxX, maxY)?.let { beams.add(it) }
                    }
                }
            }
        }
        return energized.sumOf { it.count { it } }
    }

    fun part1(input: List<String>): Int {
        return solve(input, mutableListOf(Beam(0, 0, Right)))
    }

    fun part2(input: List<String>): Int {
        var max = 0
        for (j in input[0].indices) {
            max = max(max, solve(input, mutableListOf(Beam(0, j, Down))))
            max = max(max, solve(input, mutableListOf(Beam(input.size - 1, j, Up))))
        }
        for (i in input.indices) {
            max = max(max, solve(input, mutableListOf(Beam(i, 0, Right))))
            max = max(max, solve(input, mutableListOf(Beam(i, input[0].length - 1, Left))))
        }
        return max
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 46)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 51)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
