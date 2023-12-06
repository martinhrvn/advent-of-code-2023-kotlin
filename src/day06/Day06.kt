package day06

import readInput
import readNumbers

data class Race(val time: Long, val distance: Long) {
  fun getWays(): Int {
    return (0..time).count { i -> (time - i) * i > distance }
  }
}

fun String.removePrefix(prefix: String): String {
    return this.removeRegex("$prefix:\\s+")
}

fun String.removeRegex(regex: String): String {
    return this.replace(regex.toRegex(), "")
}

class Day06(private val input: List<String>) {



  fun parseInputAsList(): List<Race> {
    val times = input.first().removePrefix("Time").readNumbers()
    val distances = input[1].removePrefix("Distance").readNumbers()
    return times.zip(distances) { time, distance -> Race(time, distance) }
  }

  fun parseInputAsString(): Race {
    val times =
        input.first().removePrefix("Time").removeRegex("\\s+").toLong()
    val distances =
        input[1].removePrefix("Distance").removeRegex("\\s+").toLong()
    return Race(times, distances)
  }

  fun part1(): Int {
    return parseInputAsList().fold(1) { acc, curr -> curr.getWays() * acc }
  }

  fun part2(): Int {
    return parseInputAsString().getWays()
  }
}

fun main() {
  val day06 = Day06(readInput("day06/Day06"))
  println(day06.part1())
  println(day06.part2())
}
