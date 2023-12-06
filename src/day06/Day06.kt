package day06

import readInput
import readNumbers
import removeLabel
import removeRegex

data class Race(val time: Long, val distance: Long) {
  fun getWays(): Int {
    return (0..time).count { i -> (time - i) * i > distance }
  }
}



class Day06(private val input: List<String>) {



  fun parseInputAsList(): List<Race> {
    val times = input.first().removeLabel("Time").readNumbers()
    val distances = input[1].removeLabel("Distance").readNumbers()
    return times.zip(distances) { time, distance -> Race(time, distance) }
  }

  fun parseInputAsString(): Race {
    val times =
        input.first().removeLabel("Time").removeRegex("\\s+").toLong()
    val distances =
        input[1].removeLabel("Distance").removeRegex("\\s+").toLong()
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
