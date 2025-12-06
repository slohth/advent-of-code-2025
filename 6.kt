package days

import AOCDay
import input.parseLines
import java.net.URI

object DaySix : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/6/input").toURL().parseLines()
    val testInput = listOf(
        "123 328  51 64 ",
        " 45 64  387 23 ",
        "  6 98  215 314",
        "*   +   *   +  "
    )

    override fun partOne() {
        require(addTotalProblems(testInput) == 4277556L)
        println(addTotalProblems(input))
    }

    override fun partTwo() {
        require(addTotalCephalopod(testInput) == 3263827L)
        println(addTotalCephalopod(input))
    }

    fun addTotalProblems(lines: List<String>): Long {
        var total = 0L

        val numbers = mutableListOf<MutableList<String>>()
        lines.forEach { line ->
            val digits = line.split("\\s+".toRegex()).filterNot { it.isBlank() }
            if (numbers.isEmpty()) repeat(digits.size) { numbers.add(mutableListOf()) }
            digits.forEachIndexed { index, digit -> numbers[index].add(digit) }
        }

        numbers.forEach { problem ->
            total += calculate(problem.dropLast(1).map { it.toLong() }, problem.last())
        }

        return total
    }

    fun addTotalCephalopod(lines: List<String>): Long {
        var total = 0L

        val operators = lines.last().split("\\s+".toRegex()).filterNot { it.isBlank() }
        val verticalSize = lines.size - 1

        val sizes = mutableListOf<Int>() // calculate sizes of each grid by the spaces between each operator
        var size = 0
        lines.last().forEachIndexed { i, char ->
            if (i == 0) return@forEachIndexed
            if (char != ' ') {
                sizes.add(size)
                size = 0
            } else { size++ }
            if (i == lines.last().length - 1) sizes.add(size + 1)
        }

        val numberGrids = mutableListOf<Array<Array<String>>>() // create number grids based on sizes
        var index = 0
        sizes.forEachIndexed { i, size ->
            val endIndex = index + size

            val nums = lines.dropLast(1).map { it.substring(index, endIndex) }
            val grid = Array(verticalSize) { Array(size) { "" } }
            nums.forEachIndexed { y, num ->
                num.forEachIndexed { x, char ->
                    grid[y][x] = char.toString()
                }
            }
            numberGrids.add(grid)

            index = endIndex + 1
        }

        numberGrids.forEachIndexed { i, grid -> // calculate
            total += calculate(getCephalopodNums(grid), operators[i])
        }
        return total
    }

    fun getCephalopodNums(grid: Array<Array<String>>): List<Long> {
        val nums = mutableListOf<Long>()
        for (x in 0 until grid.first().size) {
            var num = ""
            for (y in 0 until grid.size) { num += grid[y][x] }
            num = num.replace(" ", "")
            nums.add(if (num.isNotBlank()) num.toLong() else 0)
        }
        return nums
    }

    fun calculate(nums: List<Long>, operation: String): Long {
        var ans = nums.first()
        nums.drop(1).forEach { if (operation == "*") ans *= it else ans += it }
        return ans
    }
}
