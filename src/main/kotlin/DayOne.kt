import java.io.File

fun dayOne(inputDayOne: File) {
    println("Day 1")
    val result = inputDayOne
        .readLines()
        .fold(mutableListOf(mutableListOf<String>())) { current, calories ->
            when (calories) {
                "" -> {
                    current.add(mutableListOf())
                }

                else -> {
                    current.last().add(calories)
                }
            }
            return@fold current
        }
        .map { it -> it.map { it.toInt() } }
        .map { it.sum() }
        .max()
    println(result)
}