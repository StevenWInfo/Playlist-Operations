# Playlist Set Operations

This is a simple command line tool that takes two playlists and performs various set operations on them. This allows us to union two playlists together to create a new playlist with all of the songs on both playlists (without duplicates), intersection to create a playlist of just songs that the two have in common, and other similar set type operations.

It currently only works with Extended M3U filetypes.

Regular sets aren't ordered, however playlists are which creates a bit of a problem since the standard set operations don't have any method of dealing with that order. The way that they've been dealt with here is that it puts the songs in the first list given at the beginning of the result playlist in the order that they appeared in that list, and then gives any songs not in the second list that are not in the first list in the order they appeared in the second list.

I should probably come up with a snappier name. Something like Playlist Tools or Playlist Combine or something.

It's also my first time coding with Scala so the code is probably not the cleanest it could be.

## Usage

TODO

## Installation and Build Instructions

The code is only available from this github repo at the moment so you'll want to either download or clone this repo.

build: TODO
