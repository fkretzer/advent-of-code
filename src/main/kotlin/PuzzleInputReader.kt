import java.io.File

class PuzzleInputReader {
    companion object {
        fun lines(filenameInResourceFolder: String): Sequence<String> =
           File(this::class.java.getResource(filenameInResourceFolder).toURI()).bufferedReader().lineSequence()
    }
}


