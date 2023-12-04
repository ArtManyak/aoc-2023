import kotlin.math.pow

fun main() {
    fun getIntersectSize(line: String): Int {
        val winningNumbers = line.split(":")[1].split("|")[0].trim().split("\\s+".toRegex()).map { it.toInt() }.toHashSet()
        val myNumbers = line.split(":")[1].split("|")[1].trim().split("\\s+".toRegex()).map { it.toInt() }.toHashSet()
        return winningNumbers.intersect(myNumbers).size
    }

    fun part1(lines: List<String>): Int {
        return lines.sumOf { line ->
            2.0.pow((getIntersectSize(line) - 1).toDouble()).toInt()
        }
    }

    fun part2(lines: List<String>): Int {
        val counts = MutableList(lines.size) { 1 }
        for (i in lines.indices) {
            val line = lines[i]
            for (j in i + 1..i + getIntersectSize(line)) {
                counts[j] += counts[i]
            }
        }
        return counts.sum()
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 13)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 30)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
