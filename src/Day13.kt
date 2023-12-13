fun main() {
    fun solveInternal(pattern: List<String>, horizontal: Boolean, incorrect: Long? = null): Long {
        val indexesOfFirstLine = pattern.drop(1).withIndex().filter { it.value == pattern[0] }.map { it.index }
        val indexesOfLastLine = pattern.dropLast(1).withIndex().filter { it.value == pattern.last() }.map { it.index }

        for (indexOfFirstLine in indexesOfFirstLine) {
            val take = pattern.take(indexOfFirstLine + 2)
            if (take == take.reversed() && (indexOfFirstLine + 2) % 2 == 0) {
                var ans = (indexOfFirstLine + 2) / 2L
                if (horizontal) ans *= 100
                if (ans != incorrect) return ans
            }
        }

        for (indexOfLastLine in indexesOfLastLine) {
            val drop = pattern.drop(indexOfLastLine)
            if (drop == drop.reversed() && (indexOfLastLine + 1 + pattern.size) % 2 != 0) {
                var ans = (indexOfLastLine + 1 + pattern.size) / 2L
                if (horizontal) ans *= 100
                if (ans != incorrect) return ans
            }
        }

        return 0
    }

    fun solve(pattern: List<String>, incorrect: Long? = null): Long {
        val horizontal = solveInternal(pattern, true, incorrect)
        val patternRotated = mutableListOf<String>()
        for (i in pattern[0].indices) {
            patternRotated.add(pattern.map { it[i] }.joinToString(""))
        }
        val vertical = solveInternal(patternRotated, false, incorrect)
        return horizontal + vertical
    }

    fun part1(parts: List<String>): Long {
        return parts.sumOf { solve(it.split(System.lineSeparator())) }
    }

    fun part2(parts: List<String>): Long {
        return parts.sumOf {
            val pattern = it.split(System.lineSeparator()).toMutableList()
            val incorrect = solve(pattern)
            for (i in pattern.indices) {
                for (j in pattern[i].indices) {
                    val previous = if (pattern[i][j] == '#') "#" else "."
                    val toReplace = if (pattern[i][j] == '#') "." else "#"
                    pattern[i] = pattern[i].replaceRange(j..j, toReplace)
                    val ans = solve(pattern, incorrect)
                    if (ans != 0L && ans != incorrect) {
                        return@sumOf ans
                    }
                    pattern[i] = pattern[i].replaceRange(j..j, previous)
                }
            }
            throw Error("No answer")
        }
    }

    val testInput1 = readInputParts("test_1")
    check(part1(testInput1) == 405L)
    val testInput2 = readInputParts("test_2")
    check(part2(testInput2) == 400L)

    val input = readInputParts("input")
    part1(input).println()
    part2(input).println()
}
