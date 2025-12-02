import java.io.File
import kotlin.math.ceil

private val lines = File("input/day2/input.txt").readLines()
fun puzzle2(): Long {
    var invalidIdsSum = 0L
    lines[0].split(",").forEach {
        val idStart = it.split("-")[0].toLong()
        val idEnd = it.split("-")[1].toLong()

        for (id in idStart..idEnd){
                val chunks = id.toString().chunked(ceil(id.toString().length / 2.0).toInt())
                val allEqual = chunks.all { it == chunks[0] }
                if (allEqual && chunks.size == 2){
                    invalidIdsSum += id
                }
            }
    }
    return invalidIdsSum
}

fun puzzle2dot1(): Long {
    var invalidIdsSum = 0L
    lines[0].split(",").forEach {
        val idStart = it.split("-")[0].toLong()
        val idEnd = it.split("-")[1].toLong()

        for (id in idStart..idEnd){
            for(chunkSize in 1..id.toString().length / 2){
                val chunks = id.toString().chunked(chunkSize)
                val allEqual = chunks.all { it == chunks[0] }
                if(allEqual){
                    invalidIdsSum += id
                    break
                }
            }
        }
    }
    return invalidIdsSum
}