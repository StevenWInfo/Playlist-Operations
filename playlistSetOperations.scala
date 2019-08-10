package playlistSetOperations

import java.io.FileWriter
import java.io.{FileNotFoundException, IOException}

/*
 * This is my first time writing Scala code so I'm sure that this could probably
 * be done a lot better and cleaner. If I spend more time with it I'm sure it will improve.
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

    /**
     * Perhaps this and the other functions in here should be in some
     * utility Singleton or something.
     */
    def getContents(filename: String): Either[String, List[String]] = {
      val theFile = io.Source.fromFile(filename)
      try {
        Right(theFile.getLines().toList().filter(!_.isEmpty()))
      } catch {
        case e: FileNotFoundException => Left(String.format(Messages.fileNotFound, filename))
      } finally {
        theFile.close()
      }
    }

    def evalOp(firstList: List[String], op: String, secondList: List[String]): Either[String, M3U] = op match {
      case "union" => Right(M3U.union(firstList, secondList))
      case _ => Left(Messages.unrecognizedOperation)
    }

    /** Trying out a different way of handling the exceptions.
     * Also, we don't really need the Right result so just using Unit.
     */
    def writeContents(list): Either[String, Unit] = {
      val newFile = new File(outputListName)
      catching(classOf[IOException])
        .andFinally(newFile.close)
        .opt({ () =>
          val bufferedWriter = new BufferedWriter(new FileWriter(file))
          bufferedWriter.write(list)
          bufferedWriter
        }).toRight(String.format(Messages.fileNotFound, filename))
    }

    // For more complex CLI it could also return something that would let the rest of the code switch how it is operating.
    val outputMessage: Either[String, Unit] = for {
      unused <- CommandLine.checkCommand(args)
      validArgs <- CommandLine.validateArgs(args)
      operation = args(0)
      firstListName = args(1)
      secondListName = args(2)
      outputListName = args(3)
      firstLines <- getContents(firstListName)
      secondLines <- getContents(secondListName) 
      firstList <- M3U.parse(firstLines)
      secondList <- M3U.parse(secondLines)
      resultList <- evalOp(firstList, operation, secondList)
    }

    outputMessage match {
      case Left(msg) => println(msg)
      case Right(_) =>
    }
  }
}
