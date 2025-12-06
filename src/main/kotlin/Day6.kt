import java.io.File

private val lines = File("input/day6/input.txt").readLines()
fun puzzle6(): Long {
    val operandsList = mutableListOf<MutableList<Long>>()
    val operations = mutableListOf<String>()
    lines.forEachIndexed { index, s ->
        if (index < lines.size - 1) {
            val numbers = """(\d+)""".toRegex().findAll(s).map { it.value.toLong() }.toMutableList()
            numbers.forEachIndexed { numberIndex, number ->
                if (operandsList.size <= numberIndex) {
                    operandsList.add(mutableListOf(number))
                } else {
                    operandsList[numberIndex].add(number)
                }
            }
        } else {
            val operationsString = """([+*])""".toRegex().findAll(s).map { it.value }.toList()
            operations.addAll(operationsString)
        }
    }

    var result = 0L
    operandsList.forEachIndexed { index, operands ->
        val operation = operations[index]
        if (operation == "+") {
            result += operands.sum()
        } else if (operation == "*") {
            result += operands.reduce { acc, i -> acc * i }
        }
    }
    return result
}

fun puzzle6dot1(): Long {
    val operandsList = mutableListOf<MutableList<Pair<Int, String>>>()
    val operations = mutableListOf<String>()
    val emptyColumnIndexes = mutableListOf<Int>()
    lines[0].forEachIndexed { index, c ->
        if (c == ' ') {
            emptyColumnIndexes.add(index)
        }
    }
    lines.drop(1).forEachIndexed { index, s ->
        if (index < lines.size - 2) {
            s.forEachIndexed { index, c ->
                if (c != ' ') {
                    emptyColumnIndexes.remove(index)
                }
            }
        }
    }

    lines.forEachIndexed { index, s ->
        if (index < lines.size - 1) {
            var numberCount = 0
            s.forEachIndexed { index, c ->
                if(index in emptyColumnIndexes){
                    numberCount++
                    return@forEachIndexed
                }

                if (c != ' ') {
                    val rightEmptyBorder = emptyColumnIndexes.firstOrNull { it > index } ?: s.length
                    val digitPosition = rightEmptyBorder - index
                    if(operandsList.size <= numberCount){
                        operandsList.add(mutableListOf(Pair(digitPosition, c.toString())))
                    } else {
                        val number = operandsList[numberCount].find { it.first == digitPosition }
                        if(number != null){
                            val newNumber = number.second + c.toString()
                            operandsList[numberCount].remove(number)
                            operandsList[numberCount].add(Pair(digitPosition, newNumber))
                        } else {
                            operandsList[numberCount].add(Pair(digitPosition, c.toString()))
                        }
                    }
                }
            }
        } else {
            val operationsString = """([+*])""".toRegex().findAll(s).map { it.value }.toList()
            operations.addAll(operationsString)
        }
    }

    var result = 0L
    operandsList.forEachIndexed { index, operands ->
        val operation = operations[index]
        if (operation == "+") {
            result += operands.sumOf { it.second.toLong() }
        } else if (operation == "*") {
            result += operands.map { it.second.toLong() }.reduce { acc, i -> acc * i }
        }
    }
    return result
}
