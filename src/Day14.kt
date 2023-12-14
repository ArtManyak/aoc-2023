fun main() {
    fun tiltOne(tiltedInput: MutableList<MutableList<Char>>, freePlaces: MutableList<Int>, i: Int, j: Int, row: Boolean) {
        when (tiltedInput[i][j]) {
            'O' -> {
                if (freePlaces.isEmpty()) return
                val newInd = freePlaces.first()
                freePlaces.removeAt(0)
                if (row) freePlaces.add(j) else freePlaces.add(i)
                tiltedInput[i][j] = '.'
                if (row) tiltedInput[i][newInd] = 'O' else tiltedInput[newInd][j] = 'O'
            }
            '#' -> freePlaces.clear()
            '.' -> if (row) freePlaces.add(j) else freePlaces.add(i)
        }
    }

    fun tiltNorthSouth(input: List<List<Char>>, north: Boolean): List<List<Char>> {
        val tiltedInput = mutableListOf<MutableList<Char>>()
        for (line in input) tiltedInput.add(line.toMutableList())
        for (j in tiltedInput[0].indices) {
            val freePlaces = mutableListOf<Int>()
            val indices = if (north) tiltedInput.indices else tiltedInput.indices.reversed()
            for (i in indices) {
                tiltOne(tiltedInput, freePlaces, i, j, false)
            }
        }
        return tiltedInput
    }

    fun tiltEastWest(input: List<List<Char>>, east: Boolean): List<List<Char>> {
        val tiltedInput = mutableListOf<MutableList<Char>>()
        for (line in input) tiltedInput.add(line.toMutableList())
        for (i in tiltedInput.indices) {
            val freePlaces = mutableListOf<Int>()
            val indices = if (east) tiltedInput[0].indices.reversed() else tiltedInput[0].indices
            for (j in indices) {
                tiltOne(tiltedInput, freePlaces, i, j, true)
            }
        }
        return tiltedInput
    }

    fun calculateAns(tiltedInput: List<List<Char>>): Long {
        var ans = 0L
        var mult = tiltedInput.size
        for (line in tiltedInput) {
            ans += mult * line.count { it == 'O' }
            mult--
        }
        return ans
    }

    fun part1(input: List<String>): Long {
        val charList = mutableListOf<MutableList<Char>>()
        for (line in input) charList.add(line.toMutableList())
        return calculateAns(tiltNorthSouth(charList, true))
    }

    fun part2(input: List<String>): Long {
        var tiltedInput = mutableListOf<List<Char>>()
        for (line in input) tiltedInput.add(line.toMutableList())
        val hashCodes = hashMapOf<Int, Int>()
        var start = 0
        var length = 0
        for (i in 1..1000000000) {
            tiltedInput = tiltEastWest(tiltNorthSouth(tiltEastWest(tiltNorthSouth(tiltedInput, true), false), false), true).toMutableList()
            val key = tiltedInput.hashCode()
            if (hashCodes.contains(key)) {
                start = hashCodes[key]!!
                length = i - start
                break
            }
            hashCodes[key] = i
        }
        tiltedInput.clear()
        for (line in input) tiltedInput.add(line.toMutableList())
        for (i in 1..(1000000000 - start) % length + start) {
            tiltedInput = tiltEastWest(tiltNorthSouth(tiltEastWest(tiltNorthSouth(tiltedInput, true), false), false), true).toMutableList()
        }
        return calculateAns(tiltedInput)
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 136L)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 64L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
