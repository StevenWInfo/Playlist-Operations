package playlistSetOperations

/** All the messages that are output.
 */
object Messages {
  // Probably add complement and/or subtraction.
  val help = """
  Usage:
    playlistSetOperations <firstPlaylistFilename> <setOperation> <secondPlaylistFilename>

  Playlists are not strictly sets because they have order to them. Unfortunately this means we have to use some arbitrary ordering rule which may mess up any intentional ordering. It puts the songs in the first list given at the beginning of the result playlist in the order that they appeared in that list, and then gives any songs not in the second list that are not in the first list in the order they appeared in the second list.

  Current available operations:
    Union: Unions two playlists.
    Intersect: Intersects two playlists.
  """
}

class SongData(info: String, filePath: String) {
  val info = info
  val filePath = filePath

  def toFileString(): String = info + EM3U.newline + filePath
}

/**
 * Need to figure out set operations. Difficult because these are actually lists and have order. Probably just have it prioritize the order of the first list given.
 */
class EM3U(songs: List[SongData]) {
  val songs = songs

  def toFileString(): String = EM3U.header + songs.mkString(EM3U.newline)
}

object EM3U {
  // Should probably put somewhere else.
  val newline = sys.props("line.separator")

  val header = "#EXTM3U"

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
