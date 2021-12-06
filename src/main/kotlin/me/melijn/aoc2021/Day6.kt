package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Day6 {

    init {
        val input = readTodaysInput().split(',').map { it.toInt() }
        howMuchFish(input, 80) // p1
        howMuchFish(input, 256) // p2
    }

    private fun howMuchFish(input: List<Int>, days: Int) {
        val map = HashMap<String, Long>() // days -> amount
        for (i in input) map["$i-"] = map.getOrDefault("$i-", 0) + 1
        repeat(days) {
            for (key: String in ArrayList(map.keys)) {
                if (!key.endsWith('-')) continue
                val amount = map[key] ?: 0
                val int = key.dropLast(1).toInt()
                var next = int - 1
                if (int == 0) {
                    next = 6
                    map["8"] = amount
                }

                map[next.toString()] = map.getOrDefault(next.toString(), 0) + amount
                map.remove(key)
            }
            for (pair in HashMap(map)) {
                map[pair.key + "-"] = pair.value
                map.remove(pair.key)
            }
        }
        val s = map.entries.sumOf { it.value }
        info(s)
    }
}