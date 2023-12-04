package day04

import println
import readInput

data class Scratchcard(val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {

  fun getScore(): Int {
    val common = getMatchedNumbers()
    if (common == 0) {
      return 0
    }
    return 1 shl (common - 1)
  }

  fun getMatchedNumbers(): Int {
    return winningNumbers.intersect(numbers).size
  }

  companion object {
    fun parse(input: String): Scratchcard {
      val (id, numbers) = input.split(": ")

      val (winningNumbers, numbersOnScratchcard) = numbers.split(" | ")
      return Scratchcard(
          id.replace("Card\\s+".toRegex(), "").toInt(),
          winningNumbers.trim().split("\\s+".toRegex()).map { it.trim().toInt() }.toSet(),
          numbersOnScratchcard.trim().split("\\s+".toRegex()).map { it.trim().toInt() }.toSet(),
      )
    }
  }
}

class ScratchcardChecker(private val input: List<String>) {

  fun part1(): Int {
    return input.sumOf { Scratchcard.parse(it).getScore() }
  }

  fun part2(): Int {
    val scratchcards =
        input.map {
          val scratchcard = Scratchcard.parse(it)
          scratchcard.id to scratchcard
        }
    val counts = scratchcards.associate { (k, _) -> k to 1 }

    val wonTickets =
        scratchcards.fold(counts) { acc, (k, v) ->
          val score = v.getMatchedNumbers()
          val tickets = acc.getOrDefault(k, 0)
          if (score > 0) {
            val updatedCounts =
                (k + 1..k + score).fold(acc) { counts, i ->
                  counts.mapValues { (k, v) -> if (k == i) v + tickets else v }
                }
            acc.plus(updatedCounts)
          } else {
            acc
          }
        }

    return wonTickets.entries.sumOf { it.value }
  }
}

fun main() {
  val testInput = readInput("day04/Day04_test")
  println(ScratchcardChecker(testInput).part1())
  check(ScratchcardChecker(testInput).part1() == 13)
  check(ScratchcardChecker(testInput).part2() == 30)

  val cube = ScratchcardChecker(readInput("day04/Day04"))
  cube.part1().println()
  cube.part2().println()
}
