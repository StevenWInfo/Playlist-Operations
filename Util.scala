package playlistOperations

import scala.util.control.Exception.catching
import java.io.{FileWriter, BufferedWriter, File, FileNotFoundException, IOException}

object Util {
    /**
     * Perhaps this and the other functions in here should be in some
     * utility Singleton or something.
     */
    def getContents(filename: String): Either[String, List[String]] = {
      val theFile = io.Source.fromFile(filename)
      try {
        Right(theFile.getLines().toList.filter(!_.isEmpty()))
      } catch {
        case e: FileNotFoundException => Left(String.format(Messages.fileNotFound, filename))
      } finally {
        theFile.close()
      }
    }

    /** Trying out a different way of handling the exceptions.
     * Also, we don't really need the Right result so just using Unit.
     */
    def writeContents(outputName: String, list: String): Either[String, Unit] = {
      val newFile = new File(outputName)
      val bufferedWriter = new BufferedWriter(new FileWriter(newFile))
      catching(classOf[IOException])
        .andFinally(bufferedWriter.close())
        .opt(bufferedWriter.write(list))
        .toRight(String.format(Messages.fileNotFound, outputName))
    }
}
