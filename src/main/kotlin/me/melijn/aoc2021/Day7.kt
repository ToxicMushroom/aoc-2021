package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput
import kotlin.math.abs

class Day7 {

    init {
        val lines = readTodaysInput().split(',').map { it.toInt() }.sorted()
        var pos1 = 0
        var lowestCost1 = Long.MAX_VALUE
        var pos2 = 0
        var lowestCost2 = Long.MAX_VALUE
        for (x in lines[0]..lines[lines.size - 1]) {
            val fuelCost = lines.sumOf { abs(it.toLong() - x) }
            if (fuelCost < lowestCost1) {
                pos1 = x
                lowestCost1 = fuelCost
            }
            val fuelCost2 = lines.sumOf { // Part 2 new cost formula
                val n = abs(it.toLong() - x)
                (n * (n+1)) / 2 // formula for sum of 1 + 2 + 3 + 4 + ... + n
            }
            if (fuelCost2 < lowestCost2) {
                pos2 = x
                lowestCost2 = fuelCost
            }
        }
        info("$pos1: $lowestCost1")
        info("$pos2: $lowestCost2")
    }
}