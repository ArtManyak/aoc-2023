fun main() {
    fun getNewNumbers(numbers: List<Long>): List<Long> {
        val newNumbers = MutableList<Long>(numbers.size - 1) { 0 }
        for (i in newNumbers.indices) {
            newNumbers[i] = numbers[i + 1] - numbers[i]
        }
        return newNumbers
    }

    fun solve(numbers: List<Long>): Long {
        if (numbers.all { it == 0L }) return 0L
        val newNumbers = getNewNumbers(numbers)
        return newNumbers.last() + solve(newNumbers)
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.split(" ").map { it.toLong() }
            numbers.last() + solve(numbers)
        }
    }

    fun solve2(numbers: List<Long>): Long {
        if (numbers.all { it == 0L }) return 0L
        val newNumbers = getNewNumbers(numbers)
        return newNumbers.first() - solve2(newNumbers)
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.split(" ").map { it.toLong() }
            numbers.first() - solve2(numbers)
        }
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 114L)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 2L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}