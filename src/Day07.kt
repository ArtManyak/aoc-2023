enum class Combination {
    HighCard, OnePair, TwoPairs, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind,
}

private val values = mutableMapOf('T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)

class Hand(private val cards: String, private val jIsJoker: Boolean) : Comparable<Hand> {

    override fun compareTo(other: Hand): Int {
        if (getCombination() != other.getCombination()) return getCombination().compareTo(other.getCombination())

        for (i in cards.indices) {
            val value = values[cards[i]] ?: (cards[i] - '0')
            val otherValue = values[other.cards[i]] ?: (other.cards[i] - '0')
            if (cards[i] != other.cards[i]) return value.compareTo(otherValue)
        }

        return 0
    }

    private fun getCombination(): Combination {
        val eachCount = cards.filter { if (jIsJoker) it != 'J' else true }.groupingBy { it }.eachCount()
        val jokers = if (jIsJoker) cards.count { it == 'J' } else 0

        return when {
            eachCount.any { it.value == 5 } -> Combination.FiveOfAKind
            eachCount.any { it.value == 4 } -> when (jokers) {
                1 -> Combination.FiveOfAKind
                else -> Combination.FourOfAKind
            }
            eachCount.any { it.value == 3 } && eachCount.any { it.value == 2 } -> Combination.FullHouse
            eachCount.any { it.value == 3 } -> when (jokers) {
                2 -> Combination.FiveOfAKind
                1 -> Combination.FourOfAKind
                else -> Combination.ThreeOfAKind
            }
            eachCount.count { it.value == 2 } == 2 -> when (jokers) {
                1 -> Combination.FullHouse
                else -> Combination.TwoPairs
            }
            eachCount.count { it.value == 2 } == 1 -> when (jokers) {
                3 -> Combination.FiveOfAKind
                2 -> Combination.FourOfAKind
                1 -> Combination.ThreeOfAKind
                else -> Combination.OnePair
            }
            else -> when (jokers) {
                5 -> Combination.FiveOfAKind
                4 -> Combination.FiveOfAKind
                3 -> Combination.FourOfAKind
                2 -> Combination.ThreeOfAKind
                1 -> Combination.OnePair
                else -> Combination.HighCard
            }
        }
    }
}

fun main() {
    fun solve(input: List<String>, jIsJoker: Boolean): Long {
        return input
            .map { Pair(Hand(it.split(' ')[0], jIsJoker), it.split(' ')[1].toLong()) }
            .sortedBy { it.first }
            .withIndex()
            .sumOf { it.value.second * (it.index + 1) }
    }

    fun part1(input: List<String>): Long {
        values['J'] = 11
        return solve(input, false)
    }

    fun part2(input: List<String>): Long {
        values['J'] = 1
        return solve(input, true)
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 6440L)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 5905L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
