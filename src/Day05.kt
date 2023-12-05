fun main() {
    fun getSeedsAndMaps(parts: List<String>): Pair<List<Long>, MutableList<List<Triple<Long, Long, Long>>>> {
        val seeds = parts[0].substringAfter(": ").split(" ").map { it.toLong() }
        val maps = mutableListOf<List<Triple<Long, Long, Long>>>()
        for (part in parts.drop(1)) {
            maps.add(part.split("\n").drop(1).map { it.split(" ") }
                .map { Triple(it[0].toLong(), it[1].toLong(), it[2].toLong()) })
        }
        return Pair(seeds, maps)
    }

    fun part1(parts: List<String>): Long {
        val (seeds, maps) = getSeedsAndMaps(parts)
        val result = mutableListOf<Long>()
        for (seed in seeds) {
            var newValue = seed
            for (map in maps) {
                for (triples in map) {
                    if (newValue in triples.second..<triples.second + triples.third) {
                        newValue = newValue + triples.first - triples.second
                        break
                    }
                }
            }
            result.add(newValue)
        }
        return result.min()
    }

    fun part2(parts: List<String>): Long {
        val (seeds, maps) = getSeedsAndMaps(parts)
        var ranges = mutableListOf<MutableList<Long>>()
        for (i in seeds.indices step 2) {
            ranges.add(mutableListOf(seeds[i], seeds[i] + seeds[i + 1] - 1))
        }
        for (map in maps) {
            val newRanges = mutableListOf<MutableList<Long>>()
            for (range in ranges) {
                var addRemain = true
                //Stupid. Why should I sort here?
                for (triples in map.sortedBy { it.second }) {
                    val shift = triples.first - triples.second
                    if (range[0] >= triples.second && range[0] <= triples.second + triples.third - 1) {
                        if (range[1] >= triples.second && range[1] <= triples.second + triples.third - 1) {
                            //полностью влезли
                            newRanges.add(mutableListOf(range[0] + shift, range[1] + shift))
                            addRemain = false
                        } else {
                            //влезли от начала до серединки+-
                            newRanges.add(mutableListOf(range[0] + shift, triples.second + triples.third - 1 + shift))
                            range[0] = triples.second + triples.third
                        }
                    } else {
                        if (range[1] >= triples.second && range[1] <= triples.second + triples.third - 1) {
                            //влезли от серединки+- до конца
                            newRanges.add(mutableListOf(triples.first, range[1] + shift))
                            range[1] = triples.second - 1
                        } else {
                            //не влезли совсем
                        }
                    }
                }
                if (addRemain) {
                    newRanges.add(range)
                }
            }
            ranges = newRanges
        }
        return ranges.minOf { it[0] }
    }

    val testInput1 = readInputParts("test_1")
    check(part1(testInput1) == 35L)
    val testInput2 = readInputParts("test_2")
    check(part2(testInput2) == 46L)

    val input = readInputParts("input")
    part1(input).println()
    part2(input).println()
}
