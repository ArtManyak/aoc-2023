fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstNumber = line.first { it.isDigit() }.digitToInt()
            val lastNumber = line.last { it.isDigit() }.digitToInt()
            (firstNumber * 10 + lastNumber)
        }
    }

    fun part2(input: List<String>): Int {
        val numbersToDigits = hashMapOf(
            "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9",
            "0" to "0", "1" to "1", "2" to "2", "3" to "3", "4" to "4", "5" to "5", "6" to "6", "7" to "7", "8" to "8", "9" to "9")
        return input.sumOf { line ->
            val firstNumber = numbersToDigits[numbersToDigits.keys.map { line.indexOf(it) to it }.filter { it.first != -1 }.minBy { it.first }.second]!!.toInt()
            val lastNumber = numbersToDigits[numbersToDigits.keys.map { line.lastIndexOf(it) to it }.filter { it.first != -1 }.maxBy { it.first }.second]!!.toInt()
            (firstNumber * 10 + lastNumber)
        }
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 142)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 281)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
