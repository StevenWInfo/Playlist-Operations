package playlistSetOperations

/*
 * I know I should put most of these in different files.
 * My defense is that it's my first time coding with Scala.
 */

/** For dealing with command line stuff.
 */
object CommandLine {
  def checkCommand(firstArg): Either[String, Unit] = if (firstArg == "help" || firstArg == "") Left(Messages.help)
}

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

  val unrecognizedOperation = "Set operation not recognized. Type in the help command to list all available operations."

  val fileNotFound = "Unable to find file %s"

  // TODO Need messages for invalid arguments.
}

class SongData(info: String, filePath: String) {
  val info = info
  val filePath = filePath

  def toFileString(): String = info ++ EM3U.newline ++ filePath
}

/**
 * Need to figure out set operations. Difficult because these are actually lists and have order. Probably just have it prioritize the order of the first list given.
 */
class EM3U(songs: List[SongData]) {
  val songs = songs

  def toFileString(): String = EM3U.header ++ songs.map(_.toFileString()).mkString(EM3U.newline)
}

object EM3U {
  // Should probably put somewhere else.
  val newline = sys.props("line.separator")

  val header = "#EXTM3U"

  def parseEM3U(fileText: String): Either[String, EM3U] {
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

/**
 * Wanted to put operation between file names, however
 * I realized it conflicted with other potential first commands.
 * Potentially change later.
 */
object Validate {
  def operatorArgs(args): Either[String, Array[String]] {
    val operation = args(0)
    val firstListName = args(1)
    val secondListName = args(2)
    val outputListName = args(3)

    Left("foo")
  }
}
