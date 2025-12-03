package days

import AOCDay
import input.parseLines
import java.net.URI

object DayThree : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/3/input").toURL().parseLines()
    val testInput = listOf(
        "987654321111111", "811111111111119", "234234234234278", "818181911112111"
    )

    override fun partOne() {
        require(countLargestVoltages(testInput, 2) == 357L)
        println(countLargestVoltages(input, 2))
    }

    override fun partTwo() {
        require(countLargestVoltages(testInput, 12) == 3121910778619L)
        println(countLargestVoltages(input, 12))
    }

    fun countLargestVoltages(lines: List<String>, length: Int): Long {
        var total = 0L
        lines.forEach { total += getLargestVoltage(it, length) }
        return total
    }

    fun getLargestVoltage(bank: String, num: Int): Long {
        val digits = bank.map { it.toString().toLong() }
        var batteries = ""

        var index = 0
        for (i in (0 until num).reversed()) {
            val list = digits.subList(index, digits.size - i)
            val largest = list.maxOf { it }
            index += list.indexOf(largest) + 1
            batteries += largest.toString()
        }

        return batteries.toLong()
    }

}
