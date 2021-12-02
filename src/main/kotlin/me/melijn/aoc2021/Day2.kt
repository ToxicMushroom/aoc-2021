package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Day2 {

    init {
        val lines = readTodaysInput().lines()
        var (x: Int, y: Int, y2: Int) = Triple(0, 0, 0)
        lines.map { it.split(' ') }
            .map { it[0] to it[1].toInt() }
            .forEach { (cmd, amount) ->
                when (cmd) {
                    "up" -> y -= amount
                    "down" -> y += amount
                    "forward" -> {
                        x += amount
                        y2 += y * amount
                    }
                }
            }
        info(x * y)
        info(x * y2) // used var y as aim
    }
}