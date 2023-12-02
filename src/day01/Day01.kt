package day01

import println
import readInput

class CalibrationReader {
    val digitMapping = mapOf(
        "one" to 1, "two" to 2, "three" to 3,
        "four" to 4, "five" to 5, "six" to 6,
        "seven" to 7, "eight" to 8, "nine" to 9
    )

    fun getCalibrationNumber(line: String): Int {
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        return "$first$last".toInt()
    }

    fun getCalibrationIncludingWords(line: String): Int {
        val first: Int = line.indices.firstNotNullOf { i ->
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
        val last: Int = line.indices.reversed().firstNotNullOf { i ->
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
    val calibrationReader = CalibrationReader()
    fun part1(input: List<String>): Int = input.sumOf {
        calibrationReader.getCalibrationNumber(it)
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        calibrationReader.getCalibrationIncludingWords(line)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 142)
    val testInput_part2 = readInput("day01/Day01_2_test")
    val result2 = part2(testInput_part2)
    check(result2 == 281) {
        "Was ${result2}"
    }

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
