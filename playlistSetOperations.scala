package playlistSetOperations

import java.io.FileWriter

/*
 * If you want to do operations with more than one playlist?
 * Perhaps just do bash piping of strings?
 *
 * Be able to parse m3u, Extended m3U, and maybe m3u8
 */

object PlaylistSetOperations {
  def main(args: Array[String]) {
    val firstListName = args(0)
    val operation = args(1)
    val secondListName = args(2)
    val outputListName = args(3)

    val firstSource = io.Source.fromFile(firstListName)
    val firstLines = try source.mkString finally source.close()

    val secondSource = io.Source.fromFile(secondListName)
    val secondLines = try source.mkString finally source.close()

    val firstList = M3U.parseM3U(firstLines)
    val secondList = M3U.parseM3U(secondLines)

    val resultList = operation match {
      case "union" => M3U.union(firstList, secondList)
    }

    /* TODO
    val finalList = ""
    val newFile = new File(outputListName)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))
    bufferedWriter.write(finalList)
    bufferedWriter.close()
     */
  }
}
