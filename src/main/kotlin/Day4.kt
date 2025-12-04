import java.io.File

private val lines = File("input/day4/input.txt").readLines()
fun puzzle4(): Int {
    val paperPositions = mutableListOf<Pair<Int, Int>>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '@') {
                paperPositions.add(Pair(x, y))
            }
        }
    }
    var accessiblePaper = 0

    paperPositions.forEach { paperPosition ->
        paperPosition.adjacents().count {
            paperPositions.contains(it)
        }.let {
            if (it < 4)
                accessiblePaper++
        }
    }

    return accessiblePaper
}

fun puzzle4dot1(): Int {
    val paperPositions = mutableListOf<Pair<Int, Int>>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '@') {
                paperPositions.add(Pair(x, y))
            }
        }
    }

    val paperToRemove = mutableListOf<Pair<Int, Int>>()
    var removedPaperCount = 0
    while (true) {
        paperPositions.forEach { paperPosition ->
            paperPosition.adjacents().count {
                paperPositions.contains(it)
            }.let {
                if (it < 4) {
                    paperToRemove.add(paperPosition)
                    removedPaperCount++
                }
            }
        }

        if(paperToRemove.isEmpty()) break
        paperPositions.removeAll(paperToRemove)
        paperToRemove.clear()
    }

    return removedPaperCount
}
