package playlistOperations

/*
 * This is my first time writing Scala code so I'm sure that this could probably
 * be done a lot better and cleaner. If I spend more time with it I'm sure it will improve.
 *
 * I know I should also probably use a more standard directory structure as well.
 *
 * If you want to do operations with more than one playlist?
 * Perhaps just do bash piping of strings?
 *
 * Be able to eventually parse m3u, Extended m3U, and maybe m3u8.
 * Potentially be able to parse other playlist formats later.
 *
 * Could probably improve with a command line parser.
 * It's so simple though that it probably doesn't need it.
 */

object PlaylistSetOperations {
  def main(args: Array[String]): Unit = {

    // For more complex CLI it could also return something that would let the rest of the code switch how it is operating.
    val outputMessage: Either[String, Unit] = for {
      unused <- CommandLine.checkCommand(args)
      validArgs <- CommandLine.validateArgs(args)
      operation = args(0)
      firstListName = args(1)
      secondListName = args(2)
      outputListName = args(3)
      firstLines <- Util.getContents(firstListName)
      secondLines <- Util.getContents(secondListName) 
      firstList <- EM3U.parse(firstLines)
      secondList <- EM3U.parse(secondLines)
      resultList <- CommandLine.evalOp(firstList, operation, secondList)
      unused <- Util.writeContents(outputListName, resultList.toFileString())
    } yield unused

    outputMessage match {
      case Left(msg) => println(msg)
      case Right(_) =>
    }
  }
}
