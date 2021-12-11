package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Day9 {

    init {
        val map = readTodaysInput().lines().map { it -> it.toCharArray().map { it.toString().toInt() } }
        var sum = 0
        sum = part1(map, sum)
        val basins = mutableListOf<Int>()
        val basinPoints = mutableSetOf<Pair<Int, Int>>()
        for (y in map.indices) for (x in 0 until map[0].size) {
            if (!isLowPoint(y, x, map)) continue
            val basinNeighbours = getBasinNeighbours(y, x, map)
            basinPoints.addAll(basinNeighbours.map { it.first to it.second })
            basins.add(basinNeighbours.size)
        }

        info(sum)
        val three = basins.sorted().reversed().take(3)
        info(three[0] * three[1] * three[2])
    }

    private fun getBasinNeighbours(y: Int, x: Int, map: List<List<Int>>): Set<Triple<Int, Int, Int>> {
        val entry = map[y][x]
        val neighbours = mutableSetOf<Triple<Int, Int, Int>>()
        neighbours.add(Triple(y, x, entry))
        forNeighbours(map, y, x) { neighbour, newY, newX ->
            if (neighbour != 9 && neighbour > entry) {
                neighbours.add(Triple(newY, newX, neighbour))
                neighbours.addAll(getBasinNeighbours(newY, newX, map))
            }
        }
        return neighbours
    }

    private fun forNeighbours(map: List<List<Int>>, y: Int, x: Int, func: (value: Int, y: Int, x: Int) -> Unit) {
        for ((i, j) in listOf((1 to 0), (-1 to 0), (0 to -1), (0 to 1))) {
            if (j == 0 && i == 0) continue
            val newX = x + i
            val newY = y + j
            if (newX < 0 || newY < 0 || newX >= map[0].size || newY >= map.size) continue
            func(map[newY][newX], newY, newX)
        }
    }

    private fun part1(map: List<List<Int>>, sum: Int): Int {
        var sum1 = sum
        for (y in map.indices) {
            for (x in 0 until map[0].size) {
                val entry = map[y][x]
                if (isLowPoint(y, x, map)) sum1 += entry + 1// low point
            }
        }
        return sum1
    }

    private fun isLowPoint(y: Int, x: Int, map: List<List<Int>>): Boolean {
        val entry = map[y][x]
        return buildList {
            forNeighbours(map, y, x) { neighbour, _, _ ->
                this.add(neighbour)
            }
        }.all { it > entry }
    }

    // Debug
//    private fun draw(
//        map: List<List<Int>>,
//        basinPoints: MutableSet<Pair<Int, Int>>
//    ) {
//        for ((y, row) in map.withIndex()) {
//            for ((x, item) in row.withIndex()) {
//                if (basinPoints.contains(y to x)) print("${ConsoleColor.GREEN}$item${ConsoleColor.RESET}")
//                else if (item == 9) print("${ConsoleColor.RED}$item${ConsoleColor.RESET}")
//                else print("${ConsoleColor.BLUE}$item${ConsoleColor.RESET}")
//            }
//            println()
//        }
//    }
}