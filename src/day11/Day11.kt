package day11

import day10.Coord
import elementPairs
import println
import readInput
import kotlin.math.abs


class Day11(val input: List<String>) {

    fun doubleRows(input: List<String>): List<String> {
        val rowsToDouble = input.map { !it.contains("#") }.reversed()
        val colsToDouble = IntArray(input[0].length) { 1 }

        input.forEachIndexed { row, rowElm ->
        rowElm.forEachIndexed {  col, elm -> if (elm == '#') {
            colsToDouble[col] = 0
        }
        }
    }
        val doubledRows = input.reversed().flatMapIndexed { index, row ->
            if (rowsToDouble[index]) listOf(row, row) else listOf(row)
        }

        val reversedCols = colsToDouble.reversed()
        // Doubling columns
        val doubledCols = doubledRows.map { row ->
            row.reversed().foldIndexed("") { index, acc, c ->
                acc + c.toString().repeat(if (reversedCols[index] == 1) 2 else 1)
            }
        }
        return doubledCols.map { row -> row.reversed() }.reversed()
    }

    fun getGalaxies(input: List<String>): List<Coord> {
        return input.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, elm -> if (elm == '#') Coord(x, y) else null }
        }
    }

    fun part1(): Int {
        val doubled = doubleRows(input)
        val galaxies = getGalaxies(doubled)
        return elementPairs(galaxies).sumOf  { (g1,g2) ->
            abs(g2.first - g1.first ) + abs(g2.second-g1.second)
        }

    }
}

fun main() {
    val day11 = Day11(readInput("day11/Day11"))
    day11.part1().println()
}