package me.melijn.aoc2021.utils

object InputUtils {
    fun readResourceAsText(file: String): String =
        this::class.java.getResource(file)?.readText()
            ?: throw IllegalArgumentException("the requested resource file: $file does not exist.")
}

fun Any.readTodaysInput(): String {
    val file = "/${javaClass.simpleName.lowercase()}.txt"
    return InputUtils.readResourceAsText(file)
}