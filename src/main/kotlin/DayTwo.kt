import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class DayTwo {
    val inputDayTwo =
        this::class.java.getResource("2.input.txt").readText().split(System.lineSeparator()).filter { it.isNotEmpty() }

    fun dayTwo() {
        val ownPointsForAllRounds = inputDayTwo
            .parseToRounds()
            .sumOf { it.ownPoints }
        println("Day 2 / 1: $ownPointsForAllRounds")


        val secondStepPoints = inputDayTwo
            .parseToRoundsWithResultAdvice()
            .sumOf { it.ownPoints }
        println("Day 2 / 2: $secondStepPoints")
    }
}

fun List<String>.parseToRounds(): List<Round> = this
    .map { roundAsString -> Round(roundAsString[2].toShape(), roundAsString[0].toShape()) }

fun List<String>.parseToRoundsWithResultAdvice(): List<Round> =
    this.map { line -> Round(own = line[0].toShape().playTo(line[2].toRoundResult()), opponent = line[0].toShape()) }


enum class RoundResult {
    WIN,
    LOOSE,
    DRAW
}

fun Char.toRoundResult(): RoundResult = when (this) {
    'X' -> RoundResult.LOOSE
    'Y' -> RoundResult.DRAW
    'Z' -> RoundResult.WIN
    else -> {
        throw IllegalArgumentException("Unknown advice how to play: $this")
    }
}

sealed class Shape(val points: Int) {
    object Rock : Shape(1)
    object Paper : Shape(2)
    object Scissors : Shape(3)
    companion object {
        fun values(): Array<Shape> {
            return arrayOf(Rock, Paper, Scissors)
        }

    }

    fun playTo(roundResult: RoundResult): Shape = when (roundResult) {
        RoundResult.WIN -> Shape.values().find { it > this }!!
        RoundResult.LOOSE -> Shape.values().find { it < this }!!
        RoundResult.DRAW -> this
    }
}


operator fun Shape.compareTo(shape: Shape): Int = when (this) {
    Shape.Rock -> {
        when (shape) {
            Shape.Rock -> 0
            Shape.Paper -> -1
            Shape.Scissors -> 1
        }
    }

    Shape.Paper -> {
        when (shape) {
            Shape.Rock -> 1
            Shape.Paper -> 0
            Shape.Scissors -> -1
        }
    }

    Shape.Scissors -> {
        when (shape) {
            Shape.Rock -> -1
            Shape.Paper -> 1
            Shape.Scissors -> 0
        }
    }
}

fun Char.toShape(): Shape = when (this) {
    'A' -> Shape.Rock
    'B' -> Shape.Paper
    'C' -> Shape.Scissors
    'X' -> Shape.Rock
    'Y' -> Shape.Paper
    'Z' -> Shape.Scissors
    else -> throw IllegalArgumentException("Unknown Shape: $this")
}

class Round(val own: Shape, val opponent: Shape) {
    val ownPoints: Int
        get() = when {
            own > opponent -> 6 + own.points
            opponent > own -> 0 + own.points
            own == opponent -> 3 + own.points
            else -> {
                throw IllegalStateException("Not reachable")
            }
        }
}