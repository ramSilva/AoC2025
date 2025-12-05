import java.io.File

private val lines = File("input/day5/input.txt").readLines()
fun puzzle5(): Int {
    val freshRanges = mutableListOf<Pair<Long, Long>>()
    var isProcessingIngredients = false
    var freshCount = 0
    lines.forEach {
        if (it.isEmpty()) {
            isProcessingIngredients = true
            return@forEach
        }

        if (!isProcessingIngredients) {
            val ranges = it.split("-")
            val rangeStart = ranges[0].toLong()
            val rangeEnd = ranges[1].toLong()
            freshRanges.add(Pair(rangeStart, rangeEnd))
        } else {
            val ingredient = it.toLong()

            if (freshRanges.any { ingredient in it.first..it.second }) {
                freshCount++
            }
        }
    }

    return freshCount
}

fun puzzle5dot1(): Long {
    var freshRanges = mutableListOf<Pair<Long, Long>>()
    var isProcessingIngredients = false

    lines.forEach {
        if (it.isEmpty() || isProcessingIngredients) {
            isProcessingIngredients = true
            return@forEach
        }

        val ranges = it.split("-")
        val rangeStart = ranges[0].toLong()
        val rangeEnd = ranges[1].toLong()
        var hasOverlap = false

        freshRanges.forEachIndexed { index, range ->
            //case1: new range inside existing range
            if (rangeStart >= range.first && rangeEnd <= range.second) {
                return@forEach
            }
            //case2: new range engulfs existing range
            if (rangeStart <= range.first && rangeEnd >= range.second) {
                freshRanges[index] = Pair(rangeStart, rangeEnd)
                hasOverlap = true
            }
            //case3: new range overlaps at the start of existing range
            else if (range.first in rangeStart..rangeEnd) {
                freshRanges[index] = Pair(rangeStart, range.second)
                hasOverlap = true
            }
            //case4: new range overlaps at the end of existing range
            else if (range.second in rangeStart..rangeEnd) {
                freshRanges[index] = Pair(range.first, rangeEnd)
                hasOverlap = true
            }
        }

        if (!hasOverlap) {
            freshRanges.add(Pair(rangeStart, rangeEnd))
        }

        /* Merge overlapping ranges.
        This is needed because adding a new range can create overlaps between existing ranges.
        e.g. adding (5-15) when we have (1-10) and (12-20) creates overlap between (1-10) and (12-20)
        so we need to merge them into (1-20).

        We add (-1, -1) as a marker for ranges that have been merged and should be removed later.
        If we removed them immediately, it would affect the indices of the outer loop.
        */
        for (i in 0 until freshRanges.size) {
            val rangeA = freshRanges[i]
            val freshRangesCopy = freshRanges.toMutableList()
            for (j in i + 1 until freshRanges.size) {
                val rangeB = freshRanges[j]
                if (rangeA.second >= rangeB.first && rangeA.first <= rangeB.second) {
                    val newStart = minOf(rangeA.first, rangeB.first)
                    val newEnd = maxOf(rangeA.second, rangeB.second)
                    freshRangesCopy[i] = Pair(newStart, newEnd)
                    freshRangesCopy[j] = Pair(-1, -1)
                }
            }
            freshRanges = freshRangesCopy
        }
        freshRanges = freshRanges.filter { it.first != -1L && it.second != -1L }.toMutableList()
    }

    return freshRanges.sumOf { it.second - it.first + 1 }
}
