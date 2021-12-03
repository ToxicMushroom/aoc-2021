package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput
import java.lang.IllegalStateException
import kotlin.math.pow

class Day3 {

    init {
        val lines: List<String> = readTodaysInput().lines()
        val half: Int = lines.size / 2
        val lastIndex: Int = lines[0].length - 1
        val counts: HashMap<Int, Int> = HashMap(lastIndex + 1)

        for (line in lines) for ((index, c) in line.withIndex())
            counts[index] = counts.getOrDefault(index, 0) + c.digitToInt()

        val one: Long = counts.entries.sumOf { (key, amount) ->
            return@sumOf if (amount > half) 2.0.pow(lastIndex - key).toLong()
            else 0L
        }

        val two: Long = one.inv() and (2.0.pow(lastIndex + 1).toLong() - 1) // make sure to set other inverted bits to 0
        info(one * two)

        val mostFrequent= { i: Int, j: Float -> if (i.toFloat() >= j) 1 else 0 }
        val leastFrequent = { i: Int, j: Float -> if (i.toFloat() >= j) 0 else 1 }
        val oxygenGenRating = getLargestFrontMatchString(lines, lastIndex, mostFrequent).toInt(2)
        val co2ScrubRating = getLargestFrontMatchString(lines, lastIndex, leastFrequent).toInt(2)

        info(oxygenGenRating * co2ScrubRating)
    }

    private fun getLargestFrontMatchString(
        lines: List<String>,
        lastIndex: Int,
        importantMap: (Int, Float) -> Int
    ): String {
        var filteredLines = lines
        for (i in 0..lastIndex) {
            val count = filteredLines.count { it[i] == '1' }
            val mostImportantBit = importantMap(count, filteredLines.size/2f).digitToChar()

            filteredLines = filteredLines.filter { it[i] == mostImportantBit }
            if (filteredLines.size == 1) return filteredLines[0]
        }
        throw IllegalStateException("No largest match found")
    }
}