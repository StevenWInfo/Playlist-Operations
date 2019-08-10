package playlistOperations

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

  def evalOp(firstList: EM3U, op: String, secondList: EM3U): Either[String, EM3U] = op match {
    case "union" => Right(EM3U.union(firstList, secondList))
    case "intersection" => Right(EM3U.intersection(firstList, secondList))
    case _ => Left(Messages.unrecognizedOperation)
  }
}
