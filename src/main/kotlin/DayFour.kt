import PuzzleInputReader.Companion.lines

class DayFour {
    init {
        val example = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()
        assert(countPairsCompletelyOverlapping(example.lineSequence()) == 2)
        assert(countPairsPartiallyOverlapping(example.lineSequence()) == 4)

    }
    fun dayFour(){
        val lines = lines("4.input.txt")
        println("Day 4 / 1: ${countPairsCompletelyOverlapping(lines)}")
        val lines2 = lines("4.input.txt")
        println("Day 4 / 2: ${countPairsPartiallyOverlapping(lines2)}")
    }

    private fun countPairsPartiallyOverlapping(lines: Sequence<String>) = parseToRangePairs(lines)
        .filter { pair ->
            (pair.first().subtract(pair.last()).size == pair.first().count())
                .or(
            pair.last().subtract(pair.first()).size == pair.last().count()
                )
                .not()
        }.count()
    private fun countPairsCompletelyOverlapping(lines: Sequence<String>) = parseToRangePairs(lines)
        .filter { pair -> pair.first().subtract(pair.last()).isEmpty() or pair.last().subtract(pair.first()).isEmpty() }
        .count()

    private fun parseToRangePairs(lines: Sequence<String>) = lines.flatMap { line ->
            line.split(",")
        }.map {
            val range = it.split("-").first().toInt()
                .rangeTo(
                    it.split("-").last().toInt()
                )
            range
        }.chunked(2)

}






