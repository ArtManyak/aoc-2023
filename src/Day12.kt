fun main() {

    fun getNextPossibleLine(line: String): Sequence<String> {
        return sequence {
            if (!line.contains("?")) {
                return@sequence yield(line)
            }
            return@sequence yieldAll(
                getNextPossibleLine(line.replaceFirst("?", "#")) +
                getNextPossibleLine(line.replaceFirst("?", "."))
            )
        }
    }

    fun solveLineStupid (line: String): Long {
        val puzzle = line.split(" ")[0]
        val counts = line.split(" ")[1].split(",").map { it.toInt() }
        var ans = 0L
        for (possibleLine in getNextPossibleLine(puzzle)) {
            if (possibleLine.split(".").filter { it.isNotEmpty() }.map { it.length } == counts) ans++
        }
        return ans
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { solveLineStupid(it) }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 21L)
//    val testInput2 = readInput("test_2")
//    check(part2(testInput2) == 525152L)

    val input = readInput("input")
    part1(input).println()
//    part2(input).println()
}