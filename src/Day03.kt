fun main() {
    fun tryGetNumberAndClean(line: String, pos: Int): Pair<String, Int?> {
        if (!line[pos].isDigit()) return Pair(line, null)
        var startPos = pos
        var endPos = pos
        while (startPos > 0 && line[startPos].isDigit()) startPos--
        while (endPos < line.length - 1 && line[endPos].isDigit()) endPos++
        if (!line[startPos].isDigit()) startPos++
        if (!line[endPos].isDigit()) endPos--
        val number = line.substring(startPos..endPos)
        val newLine = line.replaceRange(startPos..endPos, ".".repeat(endPos-startPos+1))
        return Pair(newLine, number.toInt())
    }

    fun getNumbersAndCleanAround(lines: MutableList<String>, i: Int, j: Int): List<Int> {
        val res = mutableListOf<Int>()
        for (dx in listOf(-1, 0, 1)) {
            if (dx == -1 && i == 0) continue
            if (dx == 1 && i == lines.size - 1) continue
            for (dy in listOf(-1, 0, 1)) {
                if (dy == -1 && j == 0) continue
                if (dy == 1 && j == lines[i].length - 1) continue
                val (newLine, number) = tryGetNumberAndClean(lines[i + dx], j + dy)
                lines[i + dx] = newLine
                if (number != null) res.add(number)
            }
        }
        return res
    }

    fun part1(lines: MutableList<String>): Int {
        val res = mutableListOf<Int>()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (!lines[i][j].isDigit() && lines[i][j] != '.') {
                    res.addAll(getNumbersAndCleanAround(lines, i, j))
                }
            }
        }
        return res.sum()
    }

    fun part2(lines: MutableList<String>): Int {
        val res = mutableListOf<Int>()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] == '*') {
                    val numbersAround = getNumbersAndCleanAround(lines, i, j)
                    if (numbersAround.size == 2)
                        res.add(numbersAround[0] * numbersAround[1])
                }
            }
        }
        return res.sum()
    }

    val testInput1 = readInput("test_1").toMutableList()
    check(part1(testInput1) == 4361)
    val testInput2 = readInput("test_2").toMutableList()
    check(part2(testInput2) == 467835)

    val input = readInput("input").toMutableList()
    part1(input).println()
    val input2 = readInput("input").toMutableList()
    part2(input2).println()
}
