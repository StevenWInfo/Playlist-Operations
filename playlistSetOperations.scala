package playlistSetOperations

import java.io.FileWriter

/*
 * This is my first time writing Scala code so I'm sure that this could probably
 * be done a lot better and cleaner. If I spend more time with it I'm sure it will improve.
 *
 * If you want to do operations with more than one playlist?
 * Perhaps just do bash piping of strings?
 *
 * Be able to parse m3u, Extended m3U, and maybe m3u8
 *
 * Could probably improve with a command line parser.
 * It's so simple though that it probably doesn't need it.
 */

object PlaylistSetOperations {
  def main(args: Array[String]) {

    // For more complex CLI it could also return something that would let the rest of the code switch how it is operating.
    for {
      validArgs <-Validate.args(args)
      firstListName = args(0)
      operation = args(1)
      secondListName = args(2)
      outputListName = args(3)
      catching(classOf[The two file exceptions], classOf[]).opt(io.Source.fromFile(firstListName).getLines.filter(!_.isEmpty())).andFinally(_.close())
      catchind(classOf[], classOf[]).opt(io.Source.fromFile(secondListName).getLines().filter(!_.isEmpty())).andFinally(_.close())
    }

    val firstList = M3U.parseM3U(firstLines)
    val secondList = M3U.parseM3U(secondLines)

    val resultList = operation match {
      case "union" => Right(M3U.union(firstList, secondList))
      case _ => Left(Messages.unrecognizedOperation)
    }

    // Need to actually use monads (Spooky Scary!)
    val finalList: Either[String, String] = for {
      // Checks:
      // Check args (including operation)
      // Get sources
      // Maybe gettings lines
      // Parsing M3Us
    }

    // Should probably also have some error handling for this.
    def finish(list) {
      val newFile = new File(outputListName)
      val bufferedWriter = new BufferedWriter(new FileWriter(file))
      bufferedWriter.write(list)
      bufferedWriter.close()
    }

    resultList match {
      case Right(list) => finish(list)
      case Left(msg) => println(msg)
    }
  }
}
