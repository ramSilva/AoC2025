import java.io.File

private val lines = File("input/day1/input.txt").readLines()
fun puzzle1(): Int {
    var zeroCount = 0
    lines.fold(50) { position, line ->
        if(position == 0) zeroCount++
        when (line.take(1)){
            "L" -> (position - line.drop(1).toInt()) % 100
            "R" -> (position + line.drop(1).toInt()) % 100
            else -> position
        }
    }

    return zeroCount
}

fun puzzle1dot1(): Int {
    var zeroCount = 0
    lines.fold(50) { position, line ->
        val operation = line.take(1)
        val amount = line.drop(1).toInt()
        var newPosition = position

        when(operation){
            "L" -> {
                for (i in 1..amount){
                    newPosition -= 1
                    if(newPosition < 0)
                        newPosition = 99
                    if(newPosition == 0)
                        zeroCount++
                }
            }
            "R" -> {
                for (i in 1..amount){
                    newPosition += 1
                    if(newPosition > 99)
                        newPosition = 0
                    if(newPosition == 0)
                        zeroCount++
                }
            }
        }

        newPosition
    }

    return zeroCount
}