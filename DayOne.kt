import java.net.URI

object DayOne : AOCDay {

    val input = URI("https://adventofcode.com/2025/day/1/input").toURL().parseLines()
    val testInput = listOf(
        "L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82",
    )

    override fun partOne() {
        require(countZero(testInput) == 3)
        println(countZero(input))
    }

    override fun partTwo() {
        require(passwordMethod(testInput) == 6)
        println(passwordMethod(input))
    }

    fun countZero(lines: List<String>): Int {
        var dial = 50
        var hitZero = 0

        lines.map { (it[0] == 'R') to it.substring(1).toInt() }.forEach { (increase, num) ->
            dial += if (increase) num else -num
            dial = dial.mod(100)
            if (dial == 0) hitZero++
        }

        return hitZero
    }

    fun passwordMethod(lines: List<String>): Int {
        var dial = 50
        var hitZero = 0

        lines.map { (it[0] == 'R') to it.substring(1).toInt() }.forEach { (increase, num) ->

            var hitZeroThisTime = 0
            MutableList(num / 100) { 100 }.apply { add(num.mod(100)) }.forEach { split ->
                val initDial = dial

                dial += if (increase) split else -split
                dial = dial.mod(100)

                val wrapped = initDial != 0 && ((increase && dial < initDial) || (!increase && dial > initDial))
                if (dial == 0 || dial == initDial || wrapped) hitZeroThisTime++
            }
            hitZero += hitZeroThisTime

        }

        return hitZero
    }

}