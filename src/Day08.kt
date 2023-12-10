fun main() {
    class Point(val name: String) {
        lateinit var left: Point
        lateinit var right: Point
    }

    //copy-past)))
    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    //copy-past)))
    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }

    fun createPointsMap(input: List<String>): MutableMap<String, Point> {
        val pointByName = mutableMapOf<String, Point>()
        for (line in input.drop(2)) {
            val name = line.substringBefore(" ")
            val leftName = line.substringAfter("(").substringBefore(",")
            val rightName = line.substringAfter(", ").substringBefore(")")
            if (pointByName[name] == null) pointByName[name] = Point(name)
            if (pointByName[leftName] == null) pointByName[leftName] = Point(leftName)
            if (pointByName[rightName] == null) pointByName[rightName] = Point(rightName)
            pointByName[name]!!.left = pointByName[leftName]!!
            pointByName[name]!!.right = pointByName[rightName]!!
        }
        return pointByName
    }

    fun part1(input: List<String>): Long {
        val directions = input[0]
        var currentPoint = createPointsMap(input)["AAA"]!!
        var i = 0
        var steps = 0L
        while (currentPoint.name != "ZZZ") {
            when (directions[i]) {
                'L' -> currentPoint = currentPoint.left
                'R' -> currentPoint = currentPoint.right
            }
            i = (i + 1) % directions.length
            steps++
        }
        return steps
    }

    fun part2(input: List<String>): Long {
        val directions = input[0]
        val curPoints = createPointsMap(input).filter { it.key.endsWith("A") }.values
        val stepsCounts = curPoints.map {
            var currentPoint = it
            var i = 0
            var steps = 0L
            while (!currentPoint.name.endsWith("Z")) {
                when (directions[i]) {
                    'L' -> currentPoint = currentPoint.left
                    'R' -> currentPoint = currentPoint.right
                }
                i = (i + 1) % directions.length
                steps++
            }
            steps
        }

        return findLCMOfListOfNumbers(stepsCounts)
    }

    val testInput1 = readInput("test_1")
    check(part1(testInput1) == 2L)
    val testInput2 = readInput("test_2")
    check(part2(testInput2) == 6L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
