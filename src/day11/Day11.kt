package day11

import day10.Coord
import elementPairs
import println
import readInput
import kotlin.math.abs

data class Point(val x: Long, val y: Long)

data class GalaxyMap(val input: List<String>, val expansion: Long) {
    val colsToMultipy = getEmptyCols()
    val rowsToMultipy = getEmptyRows()

    fun getEmptyRows(): List<Int> {
        return input.mapIndexedNotNull { i, r -> if (!r.contains("#")) i else null}
    }

    fun getEmptyCols(): List<Int> {
        return input[0].indices.filter { i -> input.none { line -> line[i] == '#' }}
    }

    fun getGalaxies(): List<Point> {
        return input.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, elm ->
                val expandedX = colsToMultipy.count { x > it} * (expansion - 1)
                val expandedY = rowsToMultipy.count { y > it} * (expansion - 1)
                if (elm == '#')
                    Point(x.toLong() + expandedX, y.toLong() + expandedY)
                else
                    null
            }
        }
    }

    fun getGalaxyPairs(): Sequence<Pair<Point, Point>> {
        return elementPairs(getGalaxies())
    }

    fun getGalaxyDistance(g1: Point, g2: Point): Long {
        return abs(g2.x - g1.x) + abs(g2.y - g1.y)
    }

    fun getSumOfDistances(): Long {
        return getGalaxyPairs().sumOf { (g1, g2) -> getGalaxyDistance(g1, g2) }
    }
}

class Day11(val input: List<String>) {
    fun part1(): Long {
       return  GalaxyMap(input, 2).getSumOfDistances()
    }

    fun part2(): Long {
        return  GalaxyMap(input, 1000000).getSumOfDistances()
    }
}

fun main() {
    val day11 = Day11(readInput("day11/Day11"))
    day11.part1().println()
    day11.part2().println()
}