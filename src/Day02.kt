import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val gameId = game.split(":")[0].split(" ")[1].toInt()
            for (round in game.split(":")[1].split(";")) {
                for (balls in round.split(",")) {
                    val count = balls.split(" ")[1].toInt()
                    val color = balls.split(" ")[2]
                    when (color) {
                        "red" -> if (count > 12) return@sumOf 0
                        "green" -> if (count > 13) return@sumOf 0
                        "blue" -> if (count > 14) return@sumOf 0
                    }
                }
            }
            gameId
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            var (red, green, blue) = Triple(0, 0, 0)
            for (round in game.split(":")[1].split(";")) {
                for (balls in round.split(",")) {
                    val count = balls.split(" ")[1].toInt()
                    val color = balls.split(" ")[2]
                    when (color) {
                        "red" -> red = max(red, count)
                        "green" -> green = max(green, count)
                        "blue" -> blue = max(blue, count)
                    }
                }
            }
            red * green * blue
        }
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 8)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 2286)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
