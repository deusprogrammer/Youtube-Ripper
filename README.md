# YoutubeRipper Library

	In it's current state, this code is unrefined and not a distributable jar as of yet.  Currently the code only functions so much as downloading the Youtube video associated with a given URL.  Check back on later commits as I update this program.


## Ripping Algorithm

	The first part of the process is to find a line that contains "url_encoded_fmt_stream_map".  This line contains JSON data.  You can easily parse this into a HashMap with the Jackson library.

	The second part of the process is to take the map.args.url_encoded_fmt_stream_map and split it at each comma (,).  This will give you the different streams (i.e. different video qualities).

	Then take each stream and split it at each ampersand (&).  This will break it up further and give you a url, quality, itag, sig, type, and fallback_host field.  You can then easily parse these.

	Choose which stream you want and take it's sig and url field.

	Finally take the url field and replace the encoded characters with their actual characters, and then tag the value of the sig field onto the end of the url (i.e. &signature=<sig>).

	Then you can read the body of the response as the video file.

## Format of a Youtube video request

	See the FORMAT.txt file