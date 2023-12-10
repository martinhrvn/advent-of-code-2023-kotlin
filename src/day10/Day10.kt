package day10
import readInput
typealias Coord = Pair<Int,Int>


enum class Direction(val coord: Coord) {
    LEFT(Pair(-1, 0)),
    RIGHT(Pair(1, 0)),
    TOP(Pair(0, -1)),
    BOTTOM(Pair(0, 1))
}

val rightConnectable = listOf('-', 'F', 'L', 'S')
val leftConnectable = listOf('-', '7', 'J', 'S')
val bottomConnectable = listOf('|', 'F', '7', 'S')
val topConnectable = listOf('|', 'J', 'L', 'S')

data class Day10(val input: List<String>){

    val map = input.flatMapIndexed { y, row -> row.mapIndexed() { x,cell -> Pair(x, y) to cell}}.toMap()
    val start = map.filter { it.value == 'S' }.keys.first()
    val explored = mutableSetOf<Coord>()

    fun startFindingLoop(coord: Coord): List<Coord> {
        return getConnectedNeighbor(coord).firstNotNullOf {
            explored.add(coord)
            getChain(it, listOf())
        }
    }

    fun getChain(coord: Coord, chain: List<Coord>): List<Coord>? {
        var currentCoord = coord
        var currentChain = chain

        while (true) {
            val connections = getConnectedNeighbor(currentCoord)
            val con = connections.filter { it !in explored }

            if (connections.contains(start) && con.isEmpty()) {
                return currentChain
            }
            else if (con.isEmpty()) {
                return null
            }
            else {
                explored.add(currentCoord)
                currentChain = currentChain + currentCoord
                currentCoord = con.first()
            }
        }
    }

    fun getConnectedNeighbor(coord: Coord): List<Coord> {
        val (x,y) = coord
        val pipe = map[coord]

        val neighbors = listOf(Direction.LEFT, Direction.RIGHT, Direction.TOP, Direction.BOTTOM).mapNotNull { dir ->
            val neighbor = map[Pair(x + dir.coord.first, y + dir.coord.second)]
            neighbor?.let {
                if (isConnected(pipe!!, neighbor, dir)) {
                    Pair(x + dir.coord.first, y + dir.coord.second)
                } else
                    null
            }
        }

        return neighbors
    }

    fun isConnected(pipe: Char, other: Char, direction: Direction): Boolean {
        if (direction == Direction.LEFT && pipe in leftConnectable && other in rightConnectable) {
            return true
        } else if (direction == Direction.RIGHT && pipe in rightConnectable && other in leftConnectable) {
            return true
        } else if (direction == Direction.TOP && pipe in topConnectable && other in bottomConnectable) {
            return true
        } else if (direction == Direction.BOTTOM && pipe in bottomConnectable && other in topConnectable) {
            return true
        }
        return false
    }
    fun part1(): Long {
        return ((startFindingLoop(start).size + 1) / 2 + 1).toLong()
    }

    fun part2(): Long {
        return 0
    }
}

fun main() {
    val day10 = Day10(readInput("day10/Day10"))
    println(day10.part1())
    println(day10.part2())
}