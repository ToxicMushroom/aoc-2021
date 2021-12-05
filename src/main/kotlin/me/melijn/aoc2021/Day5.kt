package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

class Day5 {

    init {
        val input = readTodaysInput().lines().map { line ->
            val points = line.split(" -> ").map {
                val xy = it.split(',')
                Point(xy[0].toInt(), xy[1].toInt())
            }
            points[0] to points[1]
        }

        val board = HashMap<Int, HashMap<Int, Int>>() // y -> (x -> number of crossing lines)
        for ((point1, point2) in input) {
            if (point1.x != point2.x && point1.y != point2.y) continue // diagonals ignored
            for (x in point1.x.goodRange(point2.x)) {
                for (y in point1.y.goodRange(point2.y)) {
                    val row = board.getOrDefault(y, HashMap())
                    row[x] = row.getOrDefault(x, 0) + 1
                    board[y] = row
                }
            }
        }

        val calcTotal: (Map<Int, Map<Int, Int>>) -> Long = { b1  ->
            b1.entries.sumOf { row -> row.value.entries.sumOf { if (it.value >= 2) 1L else 0 } }
        }
        val total = calcTotal(board)
        info(total)

        for ((point1, point2) in input) {
            if (point1.x == point2.x || point1.y == point2.y) continue // non-diagonals ignored
            val xMulti = if (point2.x - point1.x > 0) 1 else -1
            val yMulti = if (point2.y - point1.y > 0) 1 else -1
            for (i in 0 .. abs(point2.x - point1.x)) {
                val x = point1.x + (i*xMulti)
                val y = point1.y + (i*yMulti)
                val row = board.getOrDefault(y, HashMap())
                row[x] = row.getOrDefault(x, 0) + 1
                board[y] = row
            }
        }

        val total2 = calcTotal(board)
        info(total2)
    }
}

fun Int.goodRange(second: Int): IntRange {
    return if (this > second) second.rangeTo(this)
    else rangeTo(second)
}