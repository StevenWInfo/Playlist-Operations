package playlistSetOperations

/*
 * I know I should put most of these in different files.
 * My defense is that it's my first time coding with Scala.
 * I'll do it later.
 */

/** For dealing with command line stuff.
 * Wanted to put operation between file names, however
 * I realized it conflicted with other potential first commands.
 * Potentially change later.
 */
object CommandLine {
  def checkCommand(args: Array[String]): Either[String, Unit] = if (args.isEmpty() || args(0).toLowerCase() == "help") Left(Messages.help) else Right(())

  def validateArgs(args: Array[String]): Either[String, Array[String]] {
    val operation = args(0)
    val firstListName = args(1)
    val secondListName = args(2)
    val outputListName = args(3)

    Left("foo")
  }
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

object SongData {
  def parse(info: String, filePath: String): Either[String, SongData] {
    Right(new SongData(info, filePath)
  }
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

  def parse(fileText: List[String]): Either[String, EM3U] {
    def checkHeader(lineList: List[String]) = lineList match {
      case head :: body if head == header => Right(Body)
      case head :: body => Left(Messages.malformedEM3U)
      case Nil => Left(Messages.emptyFile)
    }

    def parseLines(songList: List[String]): Either[String, List[SongData]] = songList match {
      case info :: song :: body => ((parsedBody: Either[String, List]) => SongData.parse(info, song).map(_ :: parsedBody))(parseLines(body))
      // Should have already checked this case
      case _ :: Nil => Left(Messages.malformedEM3U)
      case Nil => Right(Nil)
    }

    for {
      bodyLines <- checkHeader(fileText)
      unused2 <- if (bodyLines == Nil) Left(Messages.emptyFile) else Right(())
      // Unnecessary, but a little more efficient to catch here.
      unused <- if ((bodyLines.length() % 2) == 1) Left(Messages.malformedEM3U) else Right(())
      songDataList <- parseLines(bodyLines)
    } yield EM3U(songDataList)
  }
}
