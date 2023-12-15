fun main() {
    fun getHashCode(value: String) = value.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }

    fun part1(input: String): Int {
        return input.split(",").sumOf { getHashCode(it) }
    }

    fun part2(input: String): Long {
        val lenses = input.split(",")
        val boxes = List<MutableList<Pair<String, Int>>>(256) { mutableListOf() }
        for (lens in lenses) {
            if (lens.last() == '-') {
                val label = lens.dropLast(1)
                val hash = getHashCode(label)
                boxes[hash].removeIf { it.first == label }
            } else {
                val label = lens.split("=")[0]
                val focus = lens.split("=")[1].toInt()
                val hash = getHashCode(label)
                val index = boxes[hash].indexOfFirst { it.first == label }
                if (index != -1) {
                    boxes[hash].removeAt(index)
                    boxes[hash].add(index, Pair(label, focus))
                } else {
                    boxes[hash].add(Pair(label, focus))
                }
            }
        }
        var res = 0L
        for (i in boxes.indices) {
            if (boxes[i].isEmpty()) continue
            for (j in boxes[i].indices) {
                res += (i + 1) * (j + 1) * boxes[i][j].second
            }
        }
        return res
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1[0]) == 1320)
    val testInput2 = readInput("test_2")
    check(part2(testInput2[0]) == 145L)

    val input = readInput("input")
    part1(input[0]).println()
    part2(input[0]).println()
}
