import kotlin.math.abs

fun main() {

    fun dfs(map: List<String>, from: Pair<Int, Int>, to: Pair<Int, Int>, was: Set<Pair<Int, Int>>, length: Int): Int {
        if (from == to) return length
        val curWas = mutableSetOf<Pair<Int, Int>>()
        var curLength = 0
        var curPoint = from
        val neighbours = mutableListOf<Pair<Int, Int>>()
        do {
            neighbours.clear()
            if (curPoint == to) return length + curLength
            val curChar = map[curPoint.first][curPoint.second]
            for (dx in (-1..1)) {
                if (curPoint.first + dx < 0 || curPoint.first + dx == map.size) continue
                for (dy in (-1..1)) {
                    if (abs(dx) + abs(dy) == 2) continue
                    if (curChar == '>' && !(dx == 0 && dy == 1)) continue
                    if (curChar == '<' && !(dx == 0 && dy == -1)) continue
                    if (curChar == 'v' && !(dx == 1 && dy == 0)) continue
                    if (curPoint.second + dy < 0 || curPoint.second + dy == map[0].length) continue
                    val newX = curPoint.first + dx
                    val newY = curPoint.second + dy
                    val neighbour = Pair(newX, newY)
                    if (neighbour in (was + curWas)) continue
                    if (map[newX][newY] == '#') continue
                    neighbours.add(neighbour)
                }
            }
            if (neighbours.isEmpty()) return -1
            if (neighbours.size == 1) {
                curWas += neighbours.single()
                curLength++
                curPoint = neighbours.single()
            }
        } while (neighbours.size == 1)
        return neighbours.maxOf { dfs(map, it, to, was + curWas + it, length + curLength + 1) }
    }

    fun dfs2(map: List<String>, from: Pair<Int, Int>, to: Pair<Int, Int>, was: Set<Pair<Int, Int>>, length: Int): Int {
        if (from == to) return length
        val curWas = mutableSetOf<Pair<Int, Int>>()
        var curLength = 0
        var curPoint = from
        val neighbours = mutableListOf<Pair<Int, Int>>()
        do {
            neighbours.clear()
            if (curPoint == to) return length + curLength
            for (dx in (-1..1)) {
                if (curPoint.first + dx < 0 || curPoint.first + dx == map.size) continue
                for (dy in (-1..1)) {
                    if (abs(dx) + abs(dy) == 2) continue
                    if (curPoint.second + dy < 0 || curPoint.second + dy == map[0].length) continue
                    val newX = curPoint.first + dx
                    val newY = curPoint.second + dy
                    val neighbour = Pair(newX, newY)
                    if (neighbour in (was + curWas)) continue
                    if (map[newX][newY] == '#') continue
                    neighbours.add(neighbour)
                }
            }
            if (neighbours.isEmpty()) return -1
            if (neighbours.size == 1) {
                curWas += neighbours.single()
                curLength++
                curPoint = neighbours.single()
            }
        } while (neighbours.size == 1)
        return neighbours.maxOf { dfs2(map, it, to, was + curWas + it, length + curLength + 1) }
    }

    fun part1(input: List<String>): Int {
        return dfs(input, Pair(0, 1), Pair(input.size - 1, input[0].length - 2), setOf(Pair(0, 1)), 0)
    }

    fun part2(input: List<String>): Int {
        return dfs2(input, Pair(0, 1), Pair(input.size - 1, input[0].length - 2), setOf(Pair(0, 1)), 0)
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 94)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 154)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}