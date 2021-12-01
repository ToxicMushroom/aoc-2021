package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Day1 {

    init {
        val lines = readTodaysInput().lines()

        val oneCount = countIncreasesBetweenWindows(1, lines)
        info(oneCount)
        val threeCount = countIncreasesBetweenWindows(3, lines)
        info(threeCount)
    }

    private fun countIncreasesBetweenWindows(windowSize: Int, lines: List<String>): Int {
        var count = 0
        var last: Int = lines.take(windowSize).sumOf { it.toInt() }
        val largeWindow = windowSize > 2

        for (i in 1..(lines.size - windowSize)) {
            var thisWindow = last

            if (largeWindow) {
                thisWindow -= lines[i - 1].toInt()
                thisWindow += lines[i - 1 + windowSize].toInt()
            } else {
                thisWindow = 0
                for (j in 0 until windowSize) thisWindow += lines[i + j].toInt()
            }

            if (last < thisWindow) count++
            last = thisWindow
        }
        return count
    }
}