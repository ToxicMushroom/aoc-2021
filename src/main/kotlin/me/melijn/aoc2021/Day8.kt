package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput
import kotlin.math.pow
import kotlin.math.roundToInt

class Day8 {

    private val normalMap = mapOf(
        0 to "abcefg",
        1 to "cf",
        2 to "acdeg",
        3 to "acdfg",
        4 to "bcdf",
        5 to "abdfg",
        6 to "abdefg",
        7 to "acf",
        8 to "abcdefg",
        9 to "abcdfg"
    )

    init {
        val input = readTodaysInput().lines().map {
            val leftAndRight = it.split(" | ").map { part ->
                part.trim().split("\\s+".toRegex())
            }
            leftAndRight[0] to leftAndRight[1]
        }
        var count1 = 0
        var count2 = 0L
        for ((signals, display) in input) {
            for (digit in display) { // p1
                when (digit.length) {
                    2 -> count1++ // 1
                    3 -> count1++ // 7
                    4 -> count1++ // 4
                    7 -> count1++ // 8
                }
            }

            val map = HashMap<Char, Char>()
            val one = signals.first { it.length == 2 }
            val seven = signals.first { it.length == 3 }
            val four = signals.first { it.length == 4 }
            val zeroSixNine = signals.filter { it.length == 6 }
            val zeroNine = zeroSixNine.filter { it.contains(one[0]) && it.contains(one[1]) } // filter out 6
            val nine = zeroNine.first { four.all { fourChar -> it.contains(fourChar) } } // filter out 0
            val zero = zeroNine.first { it != nine }
            val six = zeroSixNine.first { it != nine && it != zero }
            val f = six.toCharArray().first { one.contains(it) }
            val c = one.replace(f.toString(), "")[0]
            map[f] = 'f'
            map[c] = 'c'
            val a = seven.first { !one.contains(it) }
            map[a] = 'a'
            val twoFive = signals.filter { it.length == 5 && !(it.contains(c) && it.contains(f)) }
            val two = twoFive.first { it.contains(c) }
            val five = twoFive.first { it.contains(f) }
            val e = two.toCharArray().first { !five.contains(it) && it != c }
            val b = five.toCharArray().first { !two.contains(it) && it != f }
            map[e] = 'e'
            map[b] = 'b'
            val d = four.first { !one.contains(it) && it != b }
            val g = nine.first { !four.contains(it) && it != a }
            map[d] = 'd'
            map[g] = 'g'

            var out = signals.toString()
            val out2 = signals.toString().toCharArray()
            map.entries.forEach { out.forEachIndexed { index, c -> if (c == it.key) out2[index] = it.value } }
            out = out2.concatToString()

            count2 += decode(display, map)
        }
        info(count1)
        info(count2)
    }

    private fun decode(display: List<String>, map: MutableMap<Char, Char>): Int =
        display.withIndex().sumOf { (index, digit) ->
            val digitChars = digit.toCharArray()
            val entry = normalMap.entries.first { (_, representation) ->
                digitChars.size == representation.length && digitChars.all { digitChar ->
                    representation.contains(map[digitChar]!!)
                }
            }
            entry.key * 10.0.pow(((3 - index).toDouble())) // reverse the index
        }.roundToInt()
}