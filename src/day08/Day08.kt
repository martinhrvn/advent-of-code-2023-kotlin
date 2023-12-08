package day08

import AdventDay
import findLCM
import readInput

data class HauntedWasteland(
    val path: Map<String, Pair<String, String>>,
    val instructions: List<String>
) {

  private fun getInstructions() = generateSequence { instructions }.flatten()

  fun getPathLength(start: String, endCondition: (str: String) -> Boolean): Int {
    return getInstructions()
        .scan(start) { acc, instr ->
          val (left, right) =
              path[acc] ?: throw NoSuchElementException("No path exists for key $acc")
          if (instr == "L") {
            left
          } else {
            right
          }
        }
        .takeWhile { endCondition(it) }
        .count()
  }

  fun findGhostPaths(): Long {
    val startPaths = path.keys.filter { it.endsWith("A") }

    val lengths = startPaths.map { path -> getPathLength(path) { !it.endsWith("Z") }.toLong() }
    return lengths.reduce(::findLCM)
  }

  companion object {
    fun parse(input: List<String>): HauntedWasteland {
      val instructions = input.first().split("").filter { it.isNotEmpty() }
      val path =
          input
              .drop(2)
              .map { line ->
                val result =
                    "([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)".toRegex().find(line)
                val (from, left, right) = result!!.groupValues
                from to (left to right)
              }
              .toMap()

      return HauntedWasteland(path, instructions)
    }
  }
}

class Day08(private val input: List<String>) : AdventDay {
  override fun part1(): Long {
    return HauntedWasteland.parse(input).getPathLength("AAA") { it != "ZZZ" }.toLong()
  }

  override fun part2(): Long {
    return HauntedWasteland.parse(input).findGhostPaths()
  }
}

fun main() {
  val day08 = Day08(readInput("day08/Day08"))
  println(day08.part1())
  println(day08.part2())
}
