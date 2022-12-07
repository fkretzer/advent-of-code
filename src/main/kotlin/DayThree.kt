import PuzzleInputReader.Companion.lines
import java.lang.IllegalArgumentException
import java.util.function.BiFunction


fun Sequence<String>.mapToRucksack(): Sequence<Rucksack> {
    return this.map {
        return@map Rucksack(
            compartmentOne = it.subSequence(0 until (it.length / 2)).toItems(),
            compartmentTwo = it.subSequence((it.length / 2)..it.lastIndex).toItems() )
    }
}
class DayThree {
    fun dayThree(){
        val inputDayThree = lines("3.input.txt")
        val dayThreeOne = inputDayThree.mapToRucksack().sumOf { it.priorityOfWrongItem() }
        println("Day 3 / 1: $dayThreeOne")


        val dayThreeTwo = inputDayThree
            .mapToRucksack()
            .chunked(3)
            .fold(0) {acc, rucksacks ->  acc.plus(findGroupBadge(rucksacks[0], rucksacks[1], rucksacks[2]).priority())}

        println("Day 3 / 2: $dayThreeTwo")
    }
}

fun CharSequence.toItems(): Sequence<Item> = this.asSequence().map { it.toItem() }

fun Char.toItem(): Item = Item(this)



fun findGroupBadge(rucksackOne: Rucksack, rucksackTwo:Rucksack, rucksackThree:Rucksack): Item {
    return (rucksackOne.allItems.distinct() + rucksackTwo.allItems.distinct() + rucksackThree.allItems.distinct())
        .groupingBy { it }
        .eachCount()
        .filter { it.value == 3 }
        .map { it.key }
        .first()
}

data class Rucksack private constructor(val compartmentOne: Sequence<Item>, val compartmentTwo: Sequence<Item>){
    val allItems: Sequence<Item>
        get() = compartmentOne + compartmentTwo
    companion object {
        operator fun invoke(compartmentOne: Sequence<Item>, compartmentTwo: Sequence<Item>): Rucksack{
            if (compartmentOne.count() != compartmentTwo.count()){
                throw IllegalArgumentException("Number of items in compartments do not match -> One: ${compartmentOne.count()} Two. ${compartmentTwo.count()}")
            }
            return Rucksack(compartmentOne, compartmentTwo)
        }
    }
    private fun wrongItem(): Item {
        return (compartmentOne.distinct() + compartmentTwo.distinct())
            .groupingBy { it }.eachCount()
            .filter { it.value > 1 }
            .map { it.key }
            .first()
    }
    fun priorityOfWrongItem(): Int {
        //compartmentOne.distinct
        return  wrongItem().priority()
    }


}

data class Item private constructor(val char: Char){
    fun priority(): Int = validCharRange.indexOf(char) + 1
    companion object {
        val validCharRange = 'a'.rangeTo('z') + 'A'.rangeTo('Z')
        operator fun invoke(char: Char): Item {
            if (char in validCharRange){
                return Item(char)
            }
            throw IllegalArgumentException("Invalid Item: $char")
        }
    }
}
