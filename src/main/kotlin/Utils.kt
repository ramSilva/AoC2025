fun Pair<Int, Int>.adjacents(): List<Pair<Int, Int>> {
    return listOf(
        Pair(this.first - 1, this.second),
        Pair(this.first + 1, this.second),
        Pair(this.first, this.second - 1),
        Pair(this.first, this.second + 1),
        Pair(this.first - 1, this.second - 1),
        Pair(this.first - 1, this.second + 1),
        Pair(this.first + 1, this.second - 1),
        Pair(this.first + 1, this.second + 1),
    )
}