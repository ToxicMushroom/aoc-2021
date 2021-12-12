package me.melijn.aoc2021

import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.readTodaysInput

class Day12 {

    init {
        val input = readTodaysInput()
        val nodes = mutableMapOf<String, List<String>>()
        input.lines().forEach {
            val startEnd = it.split("-")
            nodes[startEnd[0]] = nodes.getOrDefault(startEnd[0], emptyList()) + startEnd[1]
            nodes[startEnd[1]] = nodes.getOrDefault(startEnd[1], emptyList()) + startEnd[0]
        }

        val start = nodes["start"]!!
        var count = 0L
        val paths = HashSet<List<String>>()
        for (node in start) {
            count += countPaths(nodes, paths, listOf("start", node), false)
        }
        info(count)

        var count2 = 0L
        val paths2 = HashSet<List<String>>()
        for (node in start) {
            count2 += countPaths(nodes, paths2, listOf("start", node), true)
        }
        info(count2)
    }


    private fun countPaths(
        nodeMap: MutableMap<String, List<String>>,
        paths: HashSet<List<String>>,
        path: List<String>,
        part2: Boolean
    ): Long {
        if (!paths.add(path)) return 0
        if (path.last() == "end") return 1
        val connectedNodes = nodeMap[path.last()]!!
        var count = 0L
        for (node in connectedNodes) {
            if (node.lowercase() == node) {
                if (part2) {
                    val smallNodes = path.filter { it.lowercase() == it && it != "end" && it != "start" }
                    if (smallNodes.distinct().size == smallNodes.size && node != "start") { // no duplicate nodes yet
                        count += countPaths(nodeMap, paths, path + node, true)
                    } else if (path.contains(node)) continue
                } else if (path.contains(node)) continue
            }
            count += countPaths(nodeMap, paths, path + node, part2)
        }
        return count
    }
}