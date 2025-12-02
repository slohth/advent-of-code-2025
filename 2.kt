package days

import AOCDay
import input.parseLines
import java.net.URI

object DayTwo : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/2/input").toURL().parseLines()
    val testInput = listOf(
        "11-22","95-115","998-1012","1188511880-1188511890","222220-222224","1698522-1698528",
        "446443-446449","38593856-38593862","565653-565659","824824821-824824827","2121212118-2121212124"
    )

    override fun partOne() {
        require(count(testInput, ::isTwopeat) == 1227775554L)
        println(count(input, ::isTwopeat))
    }

    override fun partTwo() {
        require(count(testInput, ::isRepeat) == 4174379265L)
        println(count(input, ::isRepeat))
    }

    fun isTwopeat(id: Long): Boolean {
        val num = id.toString()
        if (num.length % 2 != 0) return false
        val parts = num.chunked(num.length / 2)
        return parts[0] == parts[1]
    }

    fun isRepeat(id: Long): Boolean {
        val num = id.toString()
        (1..(num.length / 2)).filter { num.length % it == 0 }.forEach { len ->
            val parts = num.chunked(len)
            if (parts.count { it == parts[0] } == parts.size && parts.size >= 2) return true
        }
        return false
    }

    fun count(lines: List<String>, predicate: (Long) -> Boolean): Long {
        val ranges = lines.map { it.split(",") }.flatten()
            .map { it.split("-") }.map { it[0].toLong()..it[1].toLong() }
        var total = 0L
        ranges.forEach { range ->
            range.forEach { id -> if (predicate(id)) total += id }
        }
        return total
    }

}
