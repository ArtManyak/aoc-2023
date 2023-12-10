fun main() {
    data class Point(val i: Int, val j: Int)

    fun findStart(input: List<String>): Point? {
        for (i in input.indices) {
            for (j in input.indices) {
                if (input[i][j] == 'S') return Point(i, j)
            }
        }
        return null
    }

//    | is a vertical pipe connecting north and south.
//    - is a horizontal pipe connecting east and west.
//    L is a 90-degree bend connecting north and east.
//    J is a 90-degree bend connecting north and west.
//    7 is a 90-degree bend connecting south and west.
//    F is a 90-degree bend connecting south and east.
//    . is ground; there is no pipe in this tile.
//    S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
    fun getNeighbours(input: List<String>, point: Point, symbolForStart: Char): List<Point> {
        val char = if (input[point.i][point.j] == 'S') symbolForStart else input[point.i][point.j]
        val toReturn = mutableListOf<Point>()
        when (char) {
            '|' -> {
                if (point.i > 0) toReturn.add(Point(point.i - 1, point.j))
                if (point.i < input.size - 1) toReturn.add(Point(point.i + 1, point.j))
            }
            '-' -> {
                if (point.j > 0) toReturn.add(Point(point.i, point.j - 1))
                if (point.j < input[point.i].length - 1) toReturn.add(Point(point.i, point.j + 1))
            }
            'L' -> {
                if (point.i > 0) toReturn.add(Point(point.i - 1, point.j))
                if (point.j < input[point.i].length - 1) toReturn.add(Point(point.i, point.j + 1))
            }
            'J' -> {
                if (point.i > 0) toReturn.add(Point(point.i - 1, point.j))
                if (point.j > 0) toReturn.add(Point(point.i, point.j - 1))
            }
            '7' -> {
                if (point.i < input.size - 1) toReturn.add(Point(point.i + 1, point.j))
                if (point.j > 0) toReturn.add(Point(point.i, point.j - 1))
            }
            'F' -> {
                if (point.i < input.size - 1) toReturn.add(Point(point.i + 1, point.j))
                if (point.j < input[point.i].length - 1) toReturn.add(Point(point.i, point.j + 1))
            }
            else -> {}
        }
        return toReturn
    }

    fun part1(input: List<String>, symbolForStart: Char): Int {
        val start = findStart(input)!!
        val was = mutableSetOf<Point>()
        val queue = mutableSetOf(start)
        val lengths = mutableMapOf(Pair(start, 0))
        while (queue.isNotEmpty()) {
            val toAdd = mutableSetOf<Point>()
            for (point in queue) {
                if (was.contains(point)) continue
                val length = lengths[point]!!
                was.add(point)
                val neighbours = getNeighbours(input, point, symbolForStart)
                for (neighbour in neighbours) {
                    if (lengths[neighbour] == null) lengths[neighbour] = length + 1
                }
                toAdd.addAll(neighbours)
            }
            queue.clear()
            queue.addAll(toAdd)
        }
        return lengths.maxOf { it.component2() }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput1 = readInput("test_1")
    val testInput2 = readInput("test_2")
    check(part1(testInput1, 'F') == 4)
    check(part1(testInput2, 'F') == 8)
//    check(part2(testInput2) == 2)

    val input = readInput("input")
    part1(input, 'J').println()
//    part2(input).println()
}