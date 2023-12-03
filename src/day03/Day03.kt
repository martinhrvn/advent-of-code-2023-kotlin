package day03

import println
import readInput

enum class Direction {
    BEFORE, AFTER
}

fun String.intVal(): Int {
    return this.toIntOrNull() ?: 0
}

fun List<String>.sum(): Int {
    return this.sumOf { it.intVal() }
}
typealias Coordinates = Pair<Int, Int>
typealias NumberMap = Map<Coordinates, Int>

class CubeConundrum(private val input: List<String>) {
    var numbersAroundSymbols = mutableMapOf<Coordinates, Int>()
    fun part1(): Int {
        input.forEachIndexed { x, row ->
            row.mapIndexed { y, c ->
                if (!c.isDigit() && c != '.') {
                    numbersAroundSymbols.putAll(getNumbersAround(x, y))
                }
            }
        }
        println(numbersAroundSymbols.filter { (_, v) -> v != 0 })
        return numbersAroundSymbols.values.sum()
    }

    fun part2(): Int {
        return input.flatMapIndexed { x, row ->
            row.mapIndexed { y, c ->
                if (!c.isDigit() && c != '.') {
                    val partNumbers = getNumbersAround(x,y)
                    if (partNumbers.size == 2) {
                        partNumbers.entries.fold(1) { acc, e -> acc * e.value}
                    } else {
                        0
                    }
                } else {
                    0
                }
            }
        }.sum()
    }

    fun getNumbersAround(x: Int, y: Int): NumberMap {
        val before = takeNumberAt(x, y, Direction.BEFORE)
        val after = takeNumberAt(x, y, Direction.AFTER)
        return mapOf(
            Pair(x, y - before.length) to before.intVal(),
            Pair(x, y + 1) to after.intVal(),
        )
            .plus(takeVertically(x - 1, y))
            .plus(takeVertically(x + 1, y)).filter { (_, v) -> v != 0 }

    }

    fun takeNumberAt(x: Int, y: Int, direction: Direction): String {
        return input[x].let { row ->
            if (direction == Direction.BEFORE) {
                row.slice(0..<y).reversed()
            } else {
                row.slice((y + 1)..<row.length)
            }
        }.takeWhile { ch ->
            ch.isDigit()
        }.let {
            if (direction == Direction.BEFORE) {
                it.reversed()
            } else {
                it
            }
        }
    }

    fun takeVertically(x: Int, y: Int): NumberMap {
        if (x < 0 || x >= input.size) {
            return mapOf()
        }

        val before = takeNumberAt(x, y, Direction.BEFORE)
        val after = takeNumberAt(x, y, Direction.AFTER)
        if (input.get(x)[y].isDigit()) {
            return mapOf(
                Pair(x, y - before.length) to
                        before.plus(input.get(x)[y]).plus(after).intVal()
            )
        } else {
            return mapOf(
                Pair(x, y - before.length) to before.intVal(),
                Pair(x, y + 1) to after.intVal()
            )
        }

    }
}


fun main() {
    val testInput = readInput("day03/Day03_test")
    println(CubeConundrum(testInput).part1())
    check(CubeConundrum(testInput).part1() == 4361)
    check(CubeConundrum(testInput).part2() == 467835)

    val cube = CubeConundrum(readInput("day03/Day03"))
    cube.part1().println()
    cube.part2().println()
}