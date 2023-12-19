fun main() {
    data class Part(val fields: Map<Char, Long>)
    class Condition(val cond: String?, val ifTrue: String) {
        fun eval(part: Part): String? {
            if (cond == null) return ifTrue
            val field = cond[0]
            val sign = cond[1]
            val value = cond.drop(2).toLong()
            when (sign) {
                '>' -> if (part.fields[field]!! > value) return ifTrue
                '<' -> if (part.fields[field]!! < value) return ifTrue
            }
            return null
        }
    }

    fun buildWorkflowMap(input: List<String>): MutableMap<String, List<Condition>> {
        val workflows = input[0].split(System.lineSeparator())
        val workflowMap = mutableMapOf<String, List<Condition>>()
        for (workflow in workflows) {
            val name = workflow.substringBefore("{")
            val conditions = workflow.substringAfter("{").dropLast(1).split(",").map {
                val split = it.split(":")
                if (split.size == 2) return@map Condition(split[0], split[1])
                Condition(null, split[0])
            }
            workflowMap[name] = conditions
        }
        return workflowMap
    }

    fun part1(input: List<String>): Long {
        val workflowMap = buildWorkflowMap(input)

        val accepted = mutableListOf<Part>()
        for (p in input[1].split(System.lineSeparator())) {
            val part = Part(p.removeSurrounding("{", "}").split(",").associate {
                it[0] to it.drop(2).toLong()
            })
            var key = "in"
            while (key != "R" && key != "A") {
                for (condition in workflowMap[key]!!) {
                    val eval = condition.eval(part) ?: continue
                    key = eval
                    break
                }
            }
            if (key == "A") accepted.add(part)
        }
        return accepted.sumOf { it.fields['x']!! + it.fields['m']!! + it.fields['a']!! + it.fields['s']!! }
    }

    fun part2(input: List<String>): Long {
        val workflowMap = buildWorkflowMap(input)

        fun req(key: String, xmas: Map<Char, IntRange>): Long {
            var x = xmas['x']!!
            var m = xmas['m']!!
            var a = xmas['a']!!
            var s = xmas['s']!!
            if (key == "A") return x.count().toLong()*m.count()*a.count()*s.count()
            if (key == "R") return 0
            var sum = 0L
            for (condition in workflowMap[key]!!) {
                if (condition.cond == null)
                    sum += req(condition.ifTrue, mapOf('x' to x, 'm' to m, 'a' to a, 's' to s))
                else {
                    val value = condition.cond.drop(2).toInt()
                    if (condition.cond[0] == 'x' && condition.cond[1] == '>') {
                        sum += req(condition.ifTrue, mapOf('x' to (value+1..x.last), 'm' to m, 'a' to a, 's' to s))
                        x = (x.first..value)
                    }
                    if (condition.cond[0] == 'x' && condition.cond[1] == '<') {
                        sum += req(condition.ifTrue, mapOf('x' to (x.first..<value), 'm' to m, 'a' to a, 's' to s))
                        x = (value..x.last)
                    }

                    if (condition.cond[0] == 'm' && condition.cond[1] == '>') {
                        sum += req(condition.ifTrue, mapOf('m' to (value+1..m.last), 'x' to x, 'a' to a, 's' to s))
                        m = (m.first..value)
                    }
                    if (condition.cond[0] == 'm' && condition.cond[1] == '<') {
                        sum += req(condition.ifTrue, mapOf('m' to (m.first..<value), 'x' to x, 'a' to a, 's' to s))
                        m = (value..m.last)
                    }

                    if (condition.cond[0] == 'a' && condition.cond[1] == '>') {
                        sum += req(condition.ifTrue, mapOf('a' to (value+1..a.last), 'm' to m, 'x' to x, 's' to s))
                        a = (a.first..value)
                    }
                    if (condition.cond[0] == 'a' && condition.cond[1] == '<') {
                        sum += req(condition.ifTrue, mapOf('a' to (a.first..<value), 'm' to m, 'x' to x, 's' to s))
                        a = (value..a.last)
                    }

                    if (condition.cond[0] == 's' && condition.cond[1] == '>') {
                        sum += req(condition.ifTrue, mapOf('s' to (value+1..s.last), 'm' to m, 'a' to a, 'x' to x))
                        s = (s.first..value)
                    }
                    if (condition.cond[0] == 's' && condition.cond[1] == '<') {
                        sum += req(condition.ifTrue, mapOf('s' to (s.first..<value), 'm' to m, 'a' to a, 'x' to x))
                        s = (value..s.last)
                    }
                }
            }
            return sum
        }
        return req("in", mapOf(
            'x' to (1..4000), 'm' to (1..4000), 'a' to (1..4000), 's' to (1..4000)))
    }

    val testInput1 = readInputParts("test_1")
    check(part1(testInput1) == 19114L)
    val testInput2 = readInputParts("test_2")
    check(part2(testInput2) == 167409079868000)

    val input = readInputParts("input")
    part1(input).println()
    part2(input).println()
}
