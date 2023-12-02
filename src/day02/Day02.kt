package day02

import println
import readInput
import kotlin.math.max

data class Colors(val red: Int, val green: Int, val blue: Int) {
    fun product(): Int {
        return red * green * blue
    }
}

data class Game(val id: Int, val rounds: List<Colors>)

val limit = Colors(12, 13, 14)

class CubeConundrum(val input: List<String>) {
    fun part1(): Int {
        return input.map(::readGame).filter { game ->
            game.rounds.all { round ->
                limit.red >= round.red && limit.blue >= round.blue && limit.green >= round.green
            }
        }.sumOf { it.id }
    }

    fun part2(): Int {
        return input.sumOf { row ->
            val game = readGame(row)
            game.rounds.fold(Colors(0, 0, 0)) { acc, round ->
                Colors(max(acc.red, round.red), max(acc.green, round.green), max(acc.blue, round.blue))
            }.product()
        }
    }

    private fun readGame(row: String): Game {
        val (gameLabel, rounds) = row.split(": ", limit = 2)
        val gameId = gameLabel.replace("Game ", "").toInt()
        val gameRounds = rounds.split(";").map { round ->
            val rs = round.trim().split(", ").map { cube ->
                val (num, color) = cube.split(" ")
                color to num.toInt()
            }.groupBy { it.first }.mapValues { (_, nums) -> nums.sumOf { it.second } }
            Colors(
                rs.getOrDefault("red", 0),
                rs.getOrDefault("green", 0),
                rs.getOrDefault("blue", 0)
            )
        }
        return Game(gameId, gameRounds)
    }
}


fun main() {
    val testInput = readInput("day02/Day02_test")
    check(CubeConundrum(testInput).part1() == 8)
    check(CubeConundrum(testInput).part2() == 2286)

    val cube = CubeConundrum(readInput("day02/Day02"))
    cube.part1().println()
    cube.part2().println()
}