package me.melijn.aoc2021

import me.melijn.aoc2021.utils.filterEmptyLines
import me.melijn.aoc2021.utils.info
import me.melijn.aoc2021.utils.numbers
import me.melijn.aoc2021.utils.readTodaysInput


typealias Board = Array<Array<Cell>>

data class Cell(
    val value: Int,
    var marked: Boolean = false
)

class Day4 {

    init {
        val input = readTodaysInput().lines().filterEmptyLines()
        val rolled = input[0].split(',').numbers()
        val boards = mutableListOf<Board>()
        for (i in 0 until (input.size / 5)) {
            val offset = i * 5 + 1

            val board = Board(5) { y ->
                val inputIndex = offset + y
                val row = input[inputIndex].trim().split("\\s+".toRegex()).numbers()

                return@Board Array(5) { rowIndex -> Cell(row[rowIndex]) }
            }
            boards.add(board)
        }

        var foundSol1 = false
        for (x in rolled) {
            markNumber(boards, x)
            var result = checkBingo(boards)
            while (result != -1) {
                if (boards.size == 1) {
                    val solution2 = calculateSolution(boards[0], x)
                    info(solution2)
                }

                if (!foundSol1) {
                    foundSol1 = true
                    val solution = calculateSolution(boards[result], x)
                    info(solution)
                }

                boards.removeAt(result)
                result = checkBingo(boards)
            }
        }
    }

    private fun calculateSolution(arrays: Board, winningNr: Int): Int = arrays.sumOf { row ->
        row.sumOf { cell -> cell.value.takeIf { !cell.marked } ?: 0 }
    } * winningNr

    private fun checkBingo(boards: List<Board>): Int {
        for ((index, board) in boards.withIndex()) {
            rowChecker@ for (row in board) {
                for (cell in row)
                    if (!cell.marked)
                        continue@rowChecker
                return index
            }
            columnChecker@ for (x in 0 until 5) {
                for (row in board)
                    if (!row[x].marked) continue@columnChecker
                return index
            }
        }
        return -1
    }

    private fun markNumber(boards: List<Board>, rolled: Int) {
        for (board in boards) for (row in board) for (cell in row)
            if (cell.value == rolled) cell.marked = true
    }
}