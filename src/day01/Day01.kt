package day01

import println
import readInput

class CalibrationReader(val input: List<String>) {
  val digitMapping =
      mapOf(
          "one" to 1,
          "two" to 2,
          "three" to 3,
          "four" to 4,
          "five" to 5,
          "six" to 6,
          "seven" to 7,
          "eight" to 8,
          "nine" to 9)

  fun part1() = input.sumOf(::getCalibrationNumber)

  fun part2() = input.sumOf(::getCalibrationIncludingWords)

  private fun getCalibrationNumber(line: String): Int {
    val first = line.first { it.isDigit() }
    val last = line.last { it.isDigit() }
    return "$first$last".toInt()
  }

  private fun getCalibrationIncludingWords(line: String): Int {
    val first: Int =
        line.indices.firstNotNullOf { i ->
          if (line[i].isDigit()) {
            line[i].digitToInt()
          } else {
            digitMapping.firstNotNullOfOrNull { (k, v) ->
              if (line.startsWith(k, startIndex = i)) {
                v
              } else null
            }
          }
        }
    val last: Int =
        line.indices.reversed().firstNotNullOf { i ->
          if (line[i].isDigit()) {
            line[i].digitToInt()
          } else {
            digitMapping.firstNotNullOfOrNull { (k, v) ->
              if (line.startsWith(k, startIndex = i)) {
                v
              } else null
            }
          }
        }
    return first * 10 + last
  }
}

fun main() {
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("day01/Day01_test")
  check(CalibrationReader(testInput).part1() == 142)
  val testInput_part2 = readInput("day01/Day01_2_test")
  check(CalibrationReader(testInput_part2).part2() == 281)

  val input = readInput("day01/Day01")
  val calibrationReader = CalibrationReader(input)
  calibrationReader.part1().println()
  calibrationReader.part2().println()
}
