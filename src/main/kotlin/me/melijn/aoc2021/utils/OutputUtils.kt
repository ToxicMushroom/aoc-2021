package me.melijn.aoc2021.utils

import org.slf4j.LoggerFactory

// Logs text to stdout with logback formatting, logback has a logger cache so this doesn't actually create new objects.
fun Any.info(text: Any) {
    LoggerFactory.getLogger(javaClass).info(text.toString())
}