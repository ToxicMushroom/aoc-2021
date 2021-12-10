package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput
import java.util.*

class Day10 {

    val charsMap = mapOf('[' to ']', '(' to ')', '{' to '}', '<' to '>',)
    val pointsMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137,)
    val points2Map = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4,)

    init {
        val input = readTodaysInput().lines()
        val illegalChars = mutableListOf<Char>()
        val scoreList = mutableListOf<Long>()

        loop1@ for (row in input) {
            val stack = Stack<Char>()
            for (c in row) {
                if (charsMap.containsKey(c)) stack.add(c)
                else {
                    val closed: Char? = stack.popOrNull()
                    val correctCloser = closed?.let { charsMap[it] }
                    if (correctCloser != c) {
                        illegalChars.add(c)
                        continue@loop1
                    }
                }
            }
            var autoComplete: Char? = stack.popOrNull() ?: continue
            var points2 = 0L
            while (autoComplete != null) {
                points2 *= 5
                points2 += points2Map[charsMap[autoComplete]]!!
                autoComplete = stack.popOrNull()
            }
            scoreList.add(points2)
        }
        scoreList.sort()
        info(illegalChars.sumOf { pointsMap[it]!! })
        info(scoreList[scoreList.size/2])
    }
}

private fun <E> Stack<E>.popOrNull(): E? {
    if (isEmpty()) return null
    return pop()
}