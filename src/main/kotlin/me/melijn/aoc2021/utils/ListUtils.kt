package me.melijn.aoc2021.utils


fun List<String>.numbers() = map { it.toInt() }
fun List<String>.filterEmptyLines() = filter { it.isNotBlank() }
