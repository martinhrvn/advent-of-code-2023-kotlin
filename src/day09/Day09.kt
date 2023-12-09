package day09

import kotlin.math.abs
import readInput

class Day09(val input: List<String>) {

  private fun getSequence() = generateSequence(0) { it + 1 }

  private fun parseInput(): List<List<Long>> {
    return input.map { it.split(" ").map { elm -> elm.toLong() } }
  }

  private fun predictNextValue(numbers: List<Long>): Long {
    val predictionRows =
        getSequence()
            .scan(numbers) { row, _ -> row.windowed(2).map { (i1, i2) -> i2 - i1 } }
            .takeWhile { it.any { n -> n != 0L } }.toList()
    return predictionRows.map { it.last() }.reversed().reduce { acc, i -> acc + i }
  }

  private fun predictPreviousValue(numbers: List<Long>): Long {
    return predictNextValue(numbers.reversed())
  }

  fun part1(): Long {
    return parseInput().map(::predictNextValue).sum()
  }

  fun part2(): Long {
    return parseInput().map(::predictPreviousValue).sum()
  }
}

fun main() {
  val day09 = Day09(readInput("day09/Day09"))
  println(day09.part1())
  println(day09.part2())
}
