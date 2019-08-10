package playlistOperations

case class SongData(info: String, filePath: String) {
  def toFileString(): String = info ++ EM3U.newline ++ filePath
}

object SongData {
  def parse(info: String, filePath: String): Either[String, SongData] = {
    Right(SongData(info, filePath))
  }
}
