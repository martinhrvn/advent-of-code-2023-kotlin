package day02

import println
import readInput

data class Game(val id: Int, val rounds: List<Map<String, Int>>)

val limit = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

class CubeConundrum(val input: List<String>) {
    fun part1(): Int {
        return input.map(::readGame).filter { game ->
            game.rounds.all { round ->
                limit.all { (color, limit) ->
                    round.get(color)?.let { it <= limit } ?: true
                }
            }
        }.sumOf { it.id }
    }

    private fun readGame(row: String): Game {
        val (gameLabel, rounds) = row.split(": ", limit = 2)
        val gameId = gameLabel.replace("Game ", "").toInt()
        val gameRounds = rounds.split(";").map { round ->
            round.trim().split(", ").map { cube ->
                val (num, color) = cube.split(" ")
                color to num.toInt()
            }.groupBy { it.first }.mapValues { (_, nums) -> nums.sumOf { it.second } }
        }
        return Game(gameId, gameRounds)
    }
}


fun main() {
//    val testInput = readInput("day02/Day02_test")
//    check(CubeConundrum(testInput).part1() == 8)

    val cube = CubeConundrum(readInput("day02/Day02"))
    cube.part1().println()
}