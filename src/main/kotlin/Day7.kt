import java.io.File

private val lines = File("input/day7/input.txt").readLines()
fun puzzle7(): Int {
    var rays = mutableSetOf<Int>()
    var splits = 0
    rays.add(lines[0].indexOf('S'))

    lines.drop(1).forEach {
        val newRays = rays.toMutableSet()
        it.forEachIndexed { index, c ->
            if (c == '^' && index in rays) {
                newRays.remove(index)
                newRays.add(index - 1)
                newRays.add(index + 1)
                splits++
            }
        }
        rays = newRays
    }

    return splits
}

fun puzzle7dot1(): Long {
    var rays = mutableListOf<Pair<Int, Long>>()
    var paths = 1L
    rays.add(Pair(lines[0].indexOf('S'), 1))

    lines.forEach { line ->
        val newRays = rays.toMutableList()
        line.forEachIndexed { index, c ->
            if (c == '^') {
                val raysFromTop = rays.find { it.first == index }?.second ?: 0
                if (raysFromTop > 0) {
                    newRays.removeIf { it.first == index }
                    paths += raysFromTop

                    val raysFromLeft = newRays.find { it.first == index - 1 }
                    val raysFromRight = newRays.find { it.first == index + 1 }

                    if (raysFromLeft != null) {
                        newRays.removeIf { it.first == index - 1 }
                        newRays.add(Pair(index - 1, raysFromLeft.second + raysFromTop))
                    } else {
                        newRays.add(Pair(index - 1, raysFromTop))
                    }

                    if (raysFromRight != null) {
                        newRays.removeIf { it.first == index + 1 }
                        newRays.add(Pair(index + 1, raysFromRight.second + raysFromTop))
                    } else {
                        newRays.add(Pair(index + 1, raysFromTop))
                    }
                }
            }
        }
        rays = newRays.sortedBy { it.first }.toMutableList()
    }

    return paths
}
