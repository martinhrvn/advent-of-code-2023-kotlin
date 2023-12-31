import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/** Reads lines from the given input txt file. */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/** Converts string to md5 hash. */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/** The cleaner shorthand for printing output. */
fun Any?.println() = println(this)

fun String.readNumbers() = this.split("\\s+".toRegex()).map { it.toLong() }

fun String.removeLabel(prefix: String): String {
  return this.removeRegex("$prefix:\\s+")
}

fun String.removeRegex(regex: String): String {
  return this.replace(regex.toRegex(), "")
}

interface AdventDay {
  fun part1(): Long

  fun part2(): Long
}

fun gcd(a: Long, b: Long): Long {
  if (b == 0L) return a
  return gcd(b, a.rem(b))
}

fun findLCM(a: Long, b: Long): Long {
  return (a * b) / gcd(a, b)
}

fun <T> elementPairs(arr: List<T>): Sequence<Pair<T, T>> =
    arr.asSequence().withIndex()
        .flatMap { (i, a) ->
            arr.subList(i + 1, arr.size).asSequence().map { b -> a to b }
        }