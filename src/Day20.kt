import java.util.LinkedList
import kotlin.time.TimeSource

enum class Pulse {
    High, Low
}
fun main() {

    open class Module() {
        val destinationModules = mutableListOf<Module>()
        val receivedPulses = mutableMapOf(Pulse.High to 0L, Pulse.Low to 0L)

        fun receivePulse(parent: Module, pulse: Pulse): Pulse? {
            receivedPulses[pulse] = receivedPulses[pulse]!! + 1
            return handlePulse(parent, pulse)
        }

        open fun handlePulse(parent: Module, pulse: Pulse): Pulse? {
            return null
        }
    }

    class BroadcasterModule(): Module() {
        override fun handlePulse(parent: Module, pulse: Pulse): Pulse {
            return pulse
        }
    }

    class FlipFlopModule(): Module() {
        private var isOn = false
        override fun handlePulse(parent: Module, pulse: Pulse): Pulse? {
            if (pulse == Pulse.Low) {
                val nextPulseToSend = if (isOn) Pulse.Low else Pulse.High
                isOn = !isOn
                return nextPulseToSend
            }
            return null
        }
    }

    class ConjunctionModule(): Module() {
        val lastPulsesFromParents = mutableMapOf<Module, Pulse>()
        override fun handlePulse(parent: Module, pulse: Pulse): Pulse {
            lastPulsesFromParents[parent] = pulse
            return if (lastPulsesFromParents.values.all { it == Pulse.High }) Pulse.Low else Pulse.High
        }

    }

    fun buildModulesMap(input: List<String>): MutableMap<String, Module> {
        val modulesMap = mutableMapOf<String, Module>()
        for (line in input) {
            val (name, _) = line.split(" ")
            when {
                name == "broadcaster" -> modulesMap[name] = BroadcasterModule()
                name[0] == '%' -> modulesMap[name.drop(1)] = FlipFlopModule()
                name[0] == '&' -> modulesMap[name.drop(1)] = ConjunctionModule()
                else -> modulesMap[name] = Module()
            }
        }
        for (line in input) {
            val name = line.split(" -> ")[0].removePrefix("&").removePrefix("%")
            val modules = line.split(" -> ")[1].split(", ").map { it.trim() }
            val parentModule = modulesMap[name]!!
            for (dest in modules) {
                if (modulesMap[dest] == null) modulesMap[dest] = Module()
                val childModule = modulesMap[dest]!!
                parentModule.destinationModules.add(childModule)
                if (childModule is ConjunctionModule) childModule.lastPulsesFromParents[parentModule] = Pulse.Low
            }
        }
        val buttonModule = Module()
        buttonModule.destinationModules.add(modulesMap["broadcaster"]!!)
        modulesMap["button"] = buttonModule
        return modulesMap
    }

    fun pressButton(
        queue: LinkedList<Pair<Module, Pulse?>>,
        modulesMap: MutableMap<String, Module>
    ) {
        queue.add(Pair(modulesMap["button"]!!, Pulse.Low))
        while (queue.isNotEmpty()) {
            val (module, pulse) = queue.remove()
            for (dest in module.destinationModules) {
                if (pulse != null) {
                    val nextPulse = dest.receivePulse(module, pulse)
                    queue.add(Pair(dest, nextPulse))
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val modulesMap = buildModulesMap(input)
        val queue = LinkedList<Pair<Module, Pulse?>>()
        for (i in (1..1000)) pressButton(queue, modulesMap)
        return modulesMap.values.sumOf { it.receivedPulses[Pulse.Low]!! } * modulesMap.values.sumOf { it.receivedPulses[Pulse.High]!! }
    }

    fun part2(input: List<String>): Long {
        val modulesMap = buildModulesMap(input)
        val queue = LinkedList<Pair<Module, Pulse?>>()
        var i = 0L
        val timeSource = TimeSource.Monotonic
        val mark0 = timeSource.markNow()
        var mark1 = timeSource.markNow()
        while (true) {
            i++
            if (i % 1000000 == 0L) {
                val now = timeSource.markNow()
                val lap = (now - mark1)
                val total = (now - mark0)
                mark1 = now
                print("$total ($lap) ")
                i.print()
                modulesMap["rx"]!!.receivedPulses.println()
            }
            if (modulesMap["rx"]!!.receivedPulses[Pulse.Low]!! > 0) return i
            pressButton(queue, modulesMap)
        }
    }

    val testInput1 = readInput("test_1")
    val testInput2 = readInput("test_2")
    check(part1(testInput1) == 32000000L)
    check(part1(testInput2) == 11687500L)

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}