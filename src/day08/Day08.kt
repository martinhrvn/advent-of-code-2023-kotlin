package day08

import AdventDay
import readInput


data class HauntedWasteland(val start: String, val path: Map<String, Pair<String, String>>, val instructions: String) {

    fun getInstructions() = generateSequence { instructions.split("").filter { it.isNotEmpty() } }.flatten()

    fun findPath(start: String, endCondition: (str: String) -> Boolean ): Int {
        return getInstructions().scan(start) { acc, instr ->
            val (left, right) = path[acc]!!
            if (instr == "L") {
                left
            } else {
                right
            }
        }.takeWhile { endCondition(it) }.count()
    }

    fun findGhostPaths(): Long {
        val startPaths = path.keys.filter { it.endsWith("A") }

        val lengths = startPaths.map { path ->
            findPath(path) { !it.endsWith("Z") }.toLong()
        }
        return lengths.reduce(::findLCM)
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm.rem(a) == 0L && lcm.rem(b) == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    companion object {
        fun parse(input: List<String>): HauntedWasteland {
            val instructions = input.first()
            val start = "AAA"
            val path = input.drop(2).map { line ->
                val result = "([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)".toRegex().find(line)
                val (from, left, right) = result!!.destructured
                from to (left to right)
            }.toMap()

            return HauntedWasteland(start, path, instructions)
        }
    }
}

class Day08(val input: List<String>): AdventDay {
    override fun part1(): Long {
        return HauntedWasteland.parse(input).findPath("AAA", { it != "ZZZ"}).toLong()
    }

    override fun part2(): Long {
        return HauntedWasteland.parse(input).findGhostPaths()
    }

}

fun main() {
    val day08 = Day08(readInput("day08/Day08"))
    println(day08.part1())
    println(day08.part2())
}