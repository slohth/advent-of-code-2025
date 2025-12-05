package days

import AOCDay
import input.parseLines
import java.net.URI

object DayFive : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/5/input").toURL().parseLines()
    val testInput = listOf(
        "3-5", "10-14", "16-20", "12-18",
        " ",
        "1", "5", "8", "11", "17", "32",
    )

    override fun partOne() {
        require(countFresh(testInput) == 3)
        println(countFresh(input))
    }

    override fun partTwo() {
        require(countFreshRanges(testInput) == 14L)
        println(countFreshRanges(input))
    }

    fun countFresh(lines: List<String>): Int {
        var count = 0

        val freshRanges = mutableListOf<LongRange>()
        val ingredients = mutableListOf<Long>()
        var isRanges = true
        lines.forEach { line ->
            if (line.isBlank()) {
                isRanges = false
                return@forEach
            }

            if (isRanges) {
                val range = line.split("-")
                freshRanges.add(range[0].toLong()..range[1].toLong())
            } else {
                ingredients.add(line.toLong())
            }
        }

        ingredients.forEach { ingredient ->
            if (freshRanges.any { it.contains(ingredient) }) count++
        }

        return count
    }

    fun countFreshRanges(lines: List<String>): Long {
        var count = 0L
        val freshRanges = mutableListOf<LongRange>()
        for (line in lines) {
            if (line.isBlank()) break
            val range = line.split("-")
            freshRanges.add(range[0].toLong()..range[1].toLong())
        }
        
        freshRanges.sortBy { it.first }
        val mergedOverlaps = mutableListOf<LongRange>()

        var previous = freshRanges[0]
        for (range in freshRanges.drop(1)) {
            if (range.first <= previous.last + 1) {
                // overlap ranges: [5-10] [11-15] -> [5-15]
                previous = previous.first..maxOf(previous.last, range.last)
            } else {
                mergedOverlaps.add(previous)
                previous = range
            }
        }
        mergedOverlaps.add(previous) // mfw I forgot to add last

        mergedOverlaps.forEach {
            count += (it.last - it.first) + 1
        }
        return count
    }
    
}
