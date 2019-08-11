# Playlist Operations

This is a simple command line tool that takes two playlists and performs various set operations on them. This allows us to union two playlists together to create a new playlist with all of the songs on both playlists (without duplicates), intersection to create a playlist of just songs that the two have in common, and other similar set type operations.

It currently only works with the Extended M3U file format. The songs in the playlist have to be from the exact same files with the same file location, song time, etc.

Regular sets aren't ordered, however playlists are which creates a bit of a problem since the standard set operations don't have any method of dealing with that order. The way that they've been dealt with here is that it puts the songs in the first list given at the beginning of the result playlist in the order that they appeared in that list, and then gives any songs not in the second list that are not in the first list in the order they appeared in the second list.

Also, if you have duplicates of the same song in a playlist, the resulting playlist may not include the duplicates.

I should probably come up with a snappier name. Something like Playlist Tools or Playlist Combine or something.

It's also my first time coding with Scala so the code is probably not the cleanest it could be.

## Usage

```bash
PlaylistOperations <setOperation> <firstPlaylistFilename> <secondPlaylistFilename>
```

The current available operations are `union` and `intersection`. As mentioned above, these operations have some quirks because they are based off of the set operations which are unordered whereas these must deal with playlists which are ordered.

TODO add set difference and possibly symmetric difference

### Examples

```bash
PlaylistOperations union PartyMix.m3u WorkoutMix.m3u HighEnergyMix.m3u
```

```bash
PlaylistOperations intersection StudyMix.m3u PartyMix.m3u StudyPartyMix.m3u
```

## Installation and Build Instructions

The code is only available from this github repo at the moment so you'll want to either download or clone this repo.

build: TODO
