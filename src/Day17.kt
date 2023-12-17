fun main() {
    data class Point(val i: Int, val j: Int, val direction: Direction, val streak: Int)

    fun solve(input: List<String>, part2: Boolean, streakRules: (Point, Direction) -> Boolean): Int {
        val costs = hashMapOf<Point, Int>()
        val points = hashSetOf<Point>()

        fun tryAddPoint(point: Point, cost: Int) {
            val currentCost = costs[point]
            if (currentCost != null && currentCost <= cost) return
            costs[point] = cost
            points.add(point)
        }

        tryAddPoint(Point(0, 0, Direction.Right, 0), 0)
        while (true) {
            val point = points.minBy { costs[it]!! }
            val cost = costs[point]!!
            points.remove(point)
            if (point.i == input.size - 1 && point.j == input[0].length - 1) {
                if (!part2) return cost
                else if (point.streak >= 4) return cost
            }
            for (newDirection in Direction.entries) {
                if (point.direction == Direction.Up && newDirection == Direction.Down) continue
                if (point.direction == Direction.Down && newDirection == Direction.Up) continue
                if (point.direction == Direction.Left && newDirection == Direction.Right) continue
                if (point.direction == Direction.Right && newDirection == Direction.Left) continue
                if (streakRules(point, newDirection)) continue
                val newI = when (newDirection) {
                    Direction.Up -> point.i - 1
                    Direction.Down -> point.i + 1
                    Direction.Left, Direction.Right -> point.i
                }
                if (newI == -1 || newI == input.size) continue
                val newJ = when (newDirection) {
                    Direction.Left -> point.j - 1
                    Direction.Right -> point.j + 1
                    Direction.Up, Direction.Down -> point.j
                }
                if (newJ == -1 || newJ == input[0].length) continue
                val newStreak = if (point.direction == newDirection) point.streak + 1 else 1
                tryAddPoint(Point(newI, newJ, newDirection, newStreak), cost + (input[newI][newJ] - '0'))
            }
        }
    }

    fun part1(input: List<String>): Int {
        return solve(input, false) { point, newDirection ->
            point.direction == newDirection && point.streak == 3
        }
    }

    fun part2(input: List<String>): Int {
        return solve(input, true) { point, newDirection ->
            (point.direction != newDirection && point.streak < 4)
                    || (point.direction == newDirection && point.streak == 10)
        }
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 102)
    val testInput2 = readInput("test_2")
    check(part2(testInput1) == 94)
    check(part2(testInput2) == 71)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
