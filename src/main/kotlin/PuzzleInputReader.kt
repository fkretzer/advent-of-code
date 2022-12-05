class PuzzleInputReader {
    companion object {
        fun lines(filenameInResourceFolder: String): Sequence<String> =
            this::class.java.getResource("3.input.txt").readText().split(System.lineSeparator()).asSequence()
    }
}


