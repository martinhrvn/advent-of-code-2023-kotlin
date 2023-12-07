package day07

import AdventDay
import readInput

enum class HandType {
  HIGH_CARD,
  ONE_PAIR,
  TWO_PAIR,
  THREE_OF_A_KIND,
  FULL_HOUSE,
  FOUR_OF_A_KIND,
  FIVE_OF_A_KIND
}

data class Hand(val cards: List<String>, val bet: Long) {

  fun getCardValue(card: String, isJoker: Boolean = false): Long {
    return when (card) {
      "A" -> 14
      "K" -> 13
      "Q" -> 12
      "J" -> if (isJoker) 1 else 11
      "T" -> 10
      else -> card.toLong()
    }
  }

  private fun groupCards() =
      cards
          .groupBy { it }
          .mapValues { (_, cards) -> cards.size }
          .entries
          .sortedByDescending { it.value }

  private fun evaluateCardCounts(counts: List<Int>): HandType =
      when (counts) {
        listOf(5) -> HandType.FIVE_OF_A_KIND
        listOf(4, 1) -> HandType.FOUR_OF_A_KIND
        listOf(3, 2) -> HandType.FULL_HOUSE
        listOf(3, 1, 1) -> HandType.THREE_OF_A_KIND
        listOf(2, 2, 1) -> HandType.TWO_PAIR
        listOf(2, 1, 1, 1) -> HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
      }

  private fun evaluateHand(): HandType {
    val counts = groupCards().map { it.value }

    return evaluateCardCounts(counts)
  }

  private fun evaluateHandWithJokers(): HandType {
    val cardCounts = groupCards()
    val jokerCount = cards.count { it == "J" }
    val maxCount = cardCounts.filter { it.key != "J" }.map { it.value }.ifEmpty { listOf(0) }
    val counts = listOf(maxCount.first() + jokerCount) + maxCount.drop(1)
    return evaluateCardCounts(counts)
  }

  fun compareTo(other: Hand, hasJokers: Boolean): Int {
    val handType1 = if (hasJokers) evaluateHandWithJokers() else evaluateHand()
    val handType2 = if (hasJokers) other.evaluateHandWithJokers() else other.evaluateHand()

    if (handType1 != handType2) {
      return handType1.compareTo(handType2)
    }

    cards.zip(other.cards).forEach { (card1, card2) ->
      val cardValue1 = getCardValue(card1, hasJokers)
      val cardValue2 = getCardValue(card2, hasJokers)
      if (cardValue1 != cardValue2) {
        return cardValue1.compareTo(cardValue2)
      }
    }

    return 0
  }

  companion object {
    fun parseHand(input: String): Hand {
      val (cards, bet) = input.split(" ")
      return Hand(cards.split("").filter { it.isNotEmpty() }, bet.toLong())
    }
  }
}

class Day07(val input: List<String>) : AdventDay {

  fun calculateScore(hasJokers: Boolean): Long =
      input
          .map { Hand.parseHand(it) }
          .sortedWith { h1, h2 -> h1.compareTo(h2, hasJokers) }
          .mapIndexed { i, v -> (i + 1) * v.bet }
          .sum()

  override fun part1(): Long {
    return calculateScore(hasJokers = false)
  }

  override fun part2(): Long {
    return calculateScore(hasJokers = true)
  }
}

fun main() {
  val day07 = Day07(readInput("day07/Day07"))
  println(day07.part1())
  println(day07.part2())
}
