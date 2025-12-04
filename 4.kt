package days

import AOCDay
import input.parseLines
import java.net.URI
import kotlin.collections.forEachIndexed

object DayFour : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/4/input").toURL().parseLines()
    val testInput = listOf(
        "..@@.@@@@.",
        "@@@.@.@.@@",
        "@@@@@.@.@@",
        "@.@@@@..@.",
        "@@.@@@@.@@",
        ".@@@@@@@.@",
        ".@.@.@.@@@",
        "@.@@@.@@@@",
        ".@@@@@@@@.",
        "@.@.@@@.@."
    )

    override fun partOne() {
        require(getRemovableRolls(testInput.map { it.toMutableList() }).size == 13)
        println(getRemovableRolls(input.map { it.toMutableList() }).size)
    }

    override fun partTwo() {
        require(getActivelyRemovableRolls(testInput.map { it.toMutableList() }).size == 43)
        println(getActivelyRemovableRolls(input.map { it.toMutableList() }).size)
    }

    fun getRemovableRolls(lines: List<List<Char>>): Set<Pair<Int, Int>> {
        val coords = mutableSetOf<Pair<Int, Int>>()

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (lines[y][x] != '@') return@forEachIndexed

                var adj = 0
                for (checkY in -1..1) {
                    for (checkX in -1..1) {
                        val newY = y + checkY
                        val newX = x + checkX
                        if (newY < 0 || newY >= lines.size) continue
                        if (newX < 0 || newX >= line.size) continue
                        if (newY == y && newX == x) continue
                        if (lines[newY][newX] == '@') adj++
                    }
                }

                if (adj < 4) coords.add(y to x)
            }
        }
        return coords
    }

    fun getActivelyRemovableRolls(lines: List<List<Char>>): Set<Pair<Int, Int>> {
        val coords = mutableSetOf<Pair<Int, Int>>()

        do {
            val lines = lines.map { it.toMutableList() }
            coords.forEach { lines[it.first][it.second] = '.' }

            val rolls = getRemovableRolls(lines)
            coords.addAll(rolls)
        } while (rolls.isNotEmpty())

        return coords
    }

}
