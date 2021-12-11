package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Octopus(
    var energy: Int,
    var flashed: Boolean
)

typealias OctoMatrix = Array<Array<Octopus>>

class Day11 {

    private val height: Int
    private val width: Int

    init {
        val input = readTodaysInput().lines().map { s -> s.toCharArray().map { it.digitToInt() } }
        height = input.size
        width = input[0].size
        val octoMatrix: OctoMatrix = Array(height) { y -> Array(width) { x -> Octopus(input[y][x], false) } }

        var flashCount = 0L
        var sync = false
        var i = 0
        while (!sync) {
            flashCount += runFlasher(octoMatrix)
            val firstEnergy = octoMatrix[0][0].energy
            sync = octoMatrix.all { row -> row.all { octo -> octo.energy == firstEnergy } }
            if (++i == 100) info("sol1: $flashCount")
            if (sync) info("sol2: $i")
        }
    }

    private fun modifyAllOcto(octoMatrix: OctoMatrix, modify: (Octopus, Int, Int) -> Unit) {
        for (y in 0 until height) for (x in 0 until width) {
            val thisOcto = octoMatrix[y][x]
            modify(thisOcto, y, x)
            octoMatrix[y][x] = thisOcto
        }
    }

    private fun runFlasher(octoMatrix: OctoMatrix): Long {
        var flashCount1 = 0L
        modifyAllOcto(octoMatrix) { octo, _, _ -> octo.energy++ }

        modifyAllOcto(octoMatrix) { octo, y, x ->
            if (octo.energy > 9) {
                octo.flashed = true
                octo.energy = 0
                goThroughNeighbours(y, x, octoMatrix)
            }
        }

        modifyAllOcto(octoMatrix) { octo, _, _ ->
            flashCount1 += if (octo.flashed) {
                octo.flashed = false
                1L
            } else 0L
        }
        return flashCount1
    }

    private fun goThroughNeighbours(y: Int, x: Int, map: OctoMatrix) {
        for (i in -1..1) for (j in -1..1) {
            if (i == 0 && j == 0) continue
            val newX = i + x
            val newY = j + y
            if (newX < 0 || newX >= width || newY < 0 || newY >= height) continue
            val thisOcto = map[newY][newX]
            if (!thisOcto.flashed) {
                thisOcto.energy++
                if (thisOcto.energy > 9) {
                    thisOcto.flashed = true
                    thisOcto.energy = 0
                    map[newY][newX] = thisOcto
                    goThroughNeighbours(newY, newX, map)
                } else map[newY][newX] = thisOcto
            }
        }
    }

// debug
//    private fun draw(octoMatrix: OctoMatrix) {
//        for (line in octoMatrix) {
//            for (octo in line) {
//                if (octo.flashed) print(ConsoleColor.BLUE)
//                print(octo.energy.toString() + "\t")
//                print(ConsoleColor.RESET)
//            }
//            println()
//        }
//        println()
//    }
}