package playlistOperations

/**
 */
case class EM3U(songs: List[SongData]) {
  def toFileString(): String = EM3U.header ++ EM3U.newline ++ songs.map(_.toFileString()).mkString(EM3U.newline)
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

  /**
   * Probably more efficient just to deal with these as lists.
   */
  def union(first: EM3U, second: EM3U): EM3U = {
    second.songs match {
      case song :: other if !first.songs.contains(song) => union(EM3U(first.songs :+ song), EM3U(other))
      case song :: other => union(first, EM3U(other))
      case Nil => first
    }
  }

  /**
   *  Could make more efficient by removing matches from both rather than just the first.
   */
  def intersection(first: EM3U, second: EM3U): EM3U = {
    first.songs match {
      case song :: other if second.songs.contains(song) => EM3U(song :: intersection(EM3U(other), second).songs)
      case song :: other => intersection(EM3U(other), second)
      case Nil => first
    }
  }
}
