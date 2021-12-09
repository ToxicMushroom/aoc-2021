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
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                if (!isLowPoint(y, x, map)) continue
                val basinNeigbours = getBasinNeighbours(y, x, map)
                basinPoints.addAll(basinNeigbours.map { it.first to it.second })
                basins.add(basinNeigbours.size)
            }
        }
        for ((y, row) in map.withIndex()) {
            for ((x, item) in row.withIndex()) {
                if (basinPoints.contains(y to x)) print(item)
                else print(".")
            }
            println()
        }
        info(sum)
        val three = basins.sorted().reversed().take(3)
        info(three[0] * three[1] * three[2])
    }

    private fun getBasinNeighbours(y: Int, x: Int, map: List<List<Int>>): Set<Triple<Int, Int, Int>> {
        val entry = map[y][x]
        val neighbours = mutableSetOf<Triple<Int, Int, Int>>()
        neighbours.add(Triple(y, x, entry))
        for (i in -1..1) {
            for (j in -1..1) {
                if (j == 0 && i == 0) continue
                val newX = x + i
                val newY = y + j
                if (newX < 0 || newY < 0 || newX >= map[0].size || newY >= map.size) continue
                val neighbour = map[newY][newX]
                if (neighbour != 9 && neighbour == entry + 1) {
                    neighbours.add(Triple(newY, newX, neighbour))
                    neighbours.addAll(getBasinNeighbours(newY, newX, map))
                }
            }
        }
        return neighbours
    }

    private fun part1(map: List<List<Int>>, sum: Int): Int {
        var sum1 = sum
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                val entry = map[y][x]
                if (isLowPoint(y, x, map)) {
                    // low point
                    sum1 += entry + 1
                }
            }
        }
        return sum1
    }

    private fun isLowPoint(y: Int, x: Int, map: List<List<Int>>): Boolean {
        val entry = map[y][x]
        val neighbours = mutableListOf<Int>()
        for (i in -1..1) {
            for (j in -1..1) {
                if (j == 0 && i == 0) continue
                val newX = x + i
                val newY = y + j
                if (newX < 0 || newY < 0 || newX >= map[0].size || newY >= map.size) continue
                neighbours.add(map[newY][newX])
            }
        }
        return neighbours.all { it > entry }
    }
}