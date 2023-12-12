package day12

import readInput


fun generateCombinations(n: Int, maxHash: Int): List<String> {
    return sequence {
        val range = 0 until (1 shl n)
        for (num in range) {
            val binaryString = num.toString(2).padStart(n, '0')
            val combination = binaryString.map { if (it == '0') '.' else '#' }.joinToString("")
            yield(combination)
        }
    }.filter { it.count { char -> char == '#' } == maxHash }
        .toList()
}

class Day12(val input: List<String>) {
    fun part1(): Long {
        val filled = input.map {
            val (springs, countsStr) = it.split(" ")
            val toFill = springs.count { it == '?' }
            val brokenCount = countsStr.split(",").map { it.toInt() }
            val brokenSprings = springs.count { it == '#' }

            val combinations = generateCombinations(toFill, brokenCount.sum() - brokenSprings)
            combinations.map { combination ->
                combination.fold(springs) { acc, char ->
                    acc.replaceFirst('?', char)
                }
            }.filter {
                it.split("\\.+".toRegex()).filter { it.isNotEmpty()
                }.map { it.length } == brokenCount
            }
        }
        return filled.map { it.size }.sum().toLong()
    }
}

fun main() {
    val day12 = Day12(readInput("day12/Day12"))
    println(day12.part1())
}