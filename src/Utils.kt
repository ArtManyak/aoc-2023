import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

fun readInputParts(name: String) = Path("src/$name.txt").readText().split("\n\n")

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
