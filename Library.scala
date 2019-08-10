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
  def checkCommand(args: Array[String]): Either[String, Unit] = if (args.isEmpty || (args(0).toLowerCase() == "help")) Left(Messages.help) else Right(())

  /**
   * TODO
   */
  def validateArgs(args: Array[String]): Either[String, Array[String]] = {
    val operation = args(0)
    val firstListName = args(1)
    val secondListName = args(2)
    val outputListName = args(3)

    //Left("foo")
    Right(args)
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

  // Eventually be able to add file name. Need a different monad to do so.
  val malformedEM3U = "A playlist file is not formatted correctly."
  // TODO Need messages for invalid arguments.
}

case class SongData(info: String, filePath: String) {
  def toFileString(): String = info ++ EM3U.newline ++ filePath
}

object SongData {
  def parse(info: String, filePath: String): Either[String, SongData] = {
    Right(SongData(info, filePath))
  }
}

/**
 * Need to figure out set operations. Difficult because these are actually lists and have order. Probably just have it prioritize the order of the first list given.
 */
case class EM3U(songs: List[SongData]) {
  def toFileString(): String = EM3U.header ++ songs.map(_.toFileString()).mkString(EM3U.newline)
}

object EM3U {
  // Should probably put somewhere else.
  val newline = sys.props("line.separator")

  val header = "#EXTM3U"

  def parse(fileText: List[String]): Either[String, EM3U] = {
    def checkHeader(lineList: List[String]) = lineList match {
      case head :: body if head == header => Right(body)
      case head :: body => Left(Messages.malformedEM3U)
      case Nil => Left(Messages.malformedEM3U)
    }

    def combineParseBody(info: String, song: String, parsedBody: Either[String, List[SongData]]): Either[String, List[SongData]] = parsedBody match {
      case Right(list) => SongData.parse(info, song).map((songData: SongData) => songData :: list)
      case error => error
    }

    def parseLines(songList: List[String]): Either[String, List[SongData]] = songList match {
      case info :: song :: body => combineParseBody(info, song, parseLines(body))
      // Should have already checked this case
      case _ :: Nil => Left(Messages.malformedEM3U)
      case Nil => Right(Nil)
    }

    for {
      bodyLines <- checkHeader(fileText)
      // Unnecessary, but a little more efficient to catch here.
      unused <- if ((bodyLines.length % 2) == 1) Left(Messages.malformedEM3U) else Right(())
      songDataList <- parseLines(bodyLines)
    } yield EM3U(songDataList)
  }

  /** TODO
   * Probably more efficient just to deal with these as lists.
   */
  def union(first: EM3U, second: EM3U): EM3U = {
    second.songs match {
      case song :: other if !first.songs.contains(song) => union(EM3U(first.songs :+ song), EM3U(other))
      case song :: other => listUnion(first, EM3U(other))
      case Nil => first
    }
  }

  /** TODO
   *  Could make more efficient by removing matches from both rather than just the first.
   */
  def intersection(first: EM3U, second: EM3U): EM3U = {
    first.songs match {
      case song :: other if second.songs.contains(song) => song :: intersection(EM3U(other), second)
      case song :: other => intersection(EM3U(other), second)
      case Nil => first
    }
  }
}
