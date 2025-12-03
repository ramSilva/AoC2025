import java.io.File
import kotlin.math.ceil
import kotlin.math.pow

private val lines = File("input/day3/input.txt").readLines()
fun puzzle3(): Int {
    return lines.sumOf {
        var digitOne = -1
        var digitTwo = -1

        it.forEachIndexed { index, c ->
            val currentDigit = c.digitToInt()
            val isLastDigit = index == it.length - 1

            if (currentDigit > digitOne) {
                if (isLastDigit) {
                    digitTwo = currentDigit
                } else {
                    digitOne = currentDigit
                    digitTwo = -1
                }
            } else if (currentDigit > digitTwo) {
                digitTwo = currentDigit
            }
        }
        digitOne * 10 + digitTwo
    }
}

fun puzzle3dot1(): Long {
    return lines.sumOf {
        var rightmostIndex = 0
        var result = 0L
        for (i in 11 downTo 0){
            var maxDigit = -1
            for (j in rightmostIndex ..< it.length - i){
                if (it[j].digitToInt() > maxDigit){
                    maxDigit = it[j].digitToInt()
                    rightmostIndex = j+1
                }
            }
            result+= maxDigit * 10.0.pow(i).toLong()
        }

        result
    }
}
