package playlistSetOperations

/** All the messages that are output.
 */
object Messages {
  // val...
}

class SongData(info: String, filePath: String) {
  val info = info
  val filePath = filePath
}

/**
 * Need to figure out set operations. Difficult because these are actually lists and have order. Probably just have it prioritize the order of the first list given.
 */
class EM3U(songs: List[SongData]) {
  val songs = songs
}

object EM3U {
  val newline = sys.props("line.separator")
  def parseEM3U(fileText: String): Either[String, EM3U] {
    val lines = fileText.split(newline)

    // Check first line for extended

    // Make sure to check for empty lines or other things
    //
    // Pass pairs of lines into SongData
    //
    // Make and EM3U out of generated list of SongData
    //
    // If there's a problem return a Left instead.
  }
}
