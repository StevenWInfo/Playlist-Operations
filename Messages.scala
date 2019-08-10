package playlistOperations

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
