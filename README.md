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

url_v8 => http://s.ytimg.com/yts/swfbin/cps-vfl7gCYpa.swf
assets => 
	html => /html5_player_template
	css => http://s.ytimg.com/yts/cssbin/www-player-vflAT-DNU.css
	css_actions => http://s.ytimg.com/yts/cssbin/www-player-actions-vflIbV886.css
	js => http://s.ytimg.com/yts/jsbin/html5player-vflPClFpS.js
url_v9as2 => http://s.ytimg.com/yts/swfbin/cps-vfl7gCYpa.swf
min_version => 8.0.0
attrs => 
	id => movie_player
args => 
	enablecsi => 1
	account_playback_token => 
	vq => auto
	enablejsapi => 1
	sendtmp => 1
	url_encoded_fmt_stream_map => url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D45%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=hd720&itag=45&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v12.cache3.c.youtube.com&sig=CED402F71D391EAABAFB2AD6C1812AAD6DF0DB03.B7DF7C50CBBCE4A9DBEC8F38F9D647FCE0072A5E,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D22%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=hd720&itag=22&type=video%2Fmp4%3B+codecs%3D%22avc1.64001F%2C+mp4a.40.2%22&fallback_host=tc.v7.cache8.c.youtube.com&sig=0DD8A344C3FC86E7E2EE876A5EA695957343A54F.AE6380142EA60A7E363CC3DB85491E875F7EFD38,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D44%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=large&itag=44&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v3.cache5.c.youtube.com&sig=525616128DCA0314974E1D204553D160EA700218.0FFF0D63AC7C60C0B4FF3E766167A62FAE3A38A6,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D35%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=large&itag=35&type=video%2Fx-flv&fallback_host=tc.v18.cache3.c.youtube.com&sig=150C044A4A0BC1D0B4DED33C88B8541CA0C119A4.64502FD633C3B874B6EDB758E81FB1881877B346,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D43%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=medium&itag=43&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v1.cache8.c.youtube.com&sig=0CDC36220AE13CFEB2F6FF20BA7A84DC2AD8F40D.A54D4E8CAF57131DE397ED597D5B658E08A2B248,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D34%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=medium&itag=34&type=video%2Fx-flv&fallback_host=tc.v2.cache3.c.youtube.com&sig=69396E9C184A64653EDA9890B9D34E85431FD42E.88BD0741D16D0984270E7C723F05EEC7C41AE39D,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D18%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=medium&itag=18&type=video%2Fmp4%3B+codecs%3D%22avc1.42001E%2C+mp4a.40.2%22&fallback_host=tc.v11.cache6.c.youtube.com&sig=B48054FA25DB1278A794CC20ADED3205B8213E6E.238D5C5FBA9E23975F2DA5678F67500DF0161C51,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D5%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=5&type=video%2Fx-flv&fallback_host=tc.v7.cache5.c.youtube.com&sig=8FC28D250D437EDC1BA3AAB6AD7BE082981F7199.18AE86F3EABF797EC8D9092D564E331CBC14BBB2,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D36%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=36&type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22&fallback_host=tc.v5.cache4.c.youtube.com&sig=50B12E1E27449E9A1055E65DB485A30A1B2E32C2.1890199429A0D9BF45F0D1898A066D6FC58DA9E7,url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D17%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=17&type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22&fallback_host=tc.v22.cache2.c.youtube.com&sig=AF7A1A4613A3230109743C9A4E8A3E2C3989B6DD.94054C800BE1CDEA9BD7789AF984B3F35B44DD02
	length_seconds => 186
	video_id => xLgzT0FcxXw
	storyboard_spec => http://i1.ytimg.com/sb/xLgzT0FcxXw/storyboard3_L$L/$N.jpg|48#27#100#10#10#0#default#q42thN7Lh6r4vUg2XV8-y0bVOIA|80#45#94#10#10#2000#M$M#MIMqVOnAdPlIYBtsb2zWMbqlc84|160#90#94#5#5#2000#M$M#DRp2pgIthxTrJmoK3h3eZHNEeOQ
	fexp => 927604,929113,909919,916611,916709,920704,912806,922403,922405,929901,913605,925710,929104,929110,929114,908493,920201,913302,919009,911116,910221,901451
	autohide => 2
	timestamp => 1358403528
	ptk => youtube_none
	abd => 1
	cr => US
	hl => en_US
	keywords => 初音ミク,Hatsune,Miku,Hatsune Miku,Project DIVA,Project DIVA f,English,Romaji,Japanese,Lyrics,Subtitles,Subs,Vocaloid,MEIKO,Meiko,Nostalogic,yuukiss
	csi_page_type => watch7
	pltype => contentugc
	fmt_list => 45/1280x720/99/0/0,22/1280x720/9/0/115,44/854x480/99/0/0,35/854x480/9/0/115,43/640x360/99/0/0,34/640x360/9/0/115,18/640x360/9/0/115,5/320x240/7/0/0,36/320x240/99/0/0,17/176x144/99/0/0
	no_get_video_log => 1
	plid => AATTdffGhXSI8uyx
	sk => bqPF4Yzhj0ci4VaTm84mX3Y2eOTueNRAC
	allow_embed => 1
	title => Project DIVA f - Nostalogic (English/Romaji subs)
	rvs => view_count=5%2C866&length_seconds=227&title=Project+DIVA+f+-+Unhappy+Refrain+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=0-jejfyp2oE,view_count=10%2C742&length_seconds=216&title=Project+DIVA+f+-+Acute+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=B9QWV2n-F8Y,view_count=6%2C100&length_seconds=207&title=Project+DIVA+f+-+Cat+Food+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=5ym66poadw0,view_count=7%2C468&length_seconds=202&title=Project+DIVA+f+-+Online+Game+Addicts+Sprechchor+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=1t3Mx-d3Q6w,view_count=16%2C247&length_seconds=212&title=Hatsune+Miku+Project+Diva+F+-+DyE&author=fine07f&id=2MIo6rmiRzY,view_count=5%2C504&length_seconds=162&title=Project+DIVA+f+-+Sadistic.Music%E2%88%9EFactory+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=wMYiC6FT6YQ,view_count=28%2C798&length_seconds=217&title=Project+Diva+-f%3A+ACUTE&author=NaThalang&id=iQjw_3uTRSM,view_count=38%2C246&length_seconds=212&title=Hatsune+Miku+%26+Megurine+Luka+-+World%27s+End+Dancehall+Project+DIVA+F+With+Eng+Translate&author=cymusic0102&id=kDD6Vbw9llI,view_count=2%2C000&length_seconds=212&title=Project+DIVA+f+-+DYE+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=Y_DcZ8wiwyE,view_count=2%2C320&length_seconds=186&title=Project+DIVA+f+-+What+Do+You+Mean%21%3F+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=obAiDec2m3k,view_count=3%2C937&length_seconds=217&title=Project+DIVA+f+-+Tengaku+%28English%2FRomaji+subs%29&author=TensaiRyuu&id=5atwerR1rQY,view_count=7%2C198&length_seconds=186&title=%E3%80%90PS+VITA%E3%80%91+%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF++Project+DIVA++%EF%BD%86+++Nostalogic&author=youngpeop007&id=g26haabqlJ4
	referrer => null
	t => vjVQa1PpcFNR31-3UM8K-GvntMnrKi1zu16_T-hdFfs=
	endscreen_module => http://s.ytimg.com/yts/swfbin/endscreen-vfl3bIPGj.swf
	tmi => 1
	watermark => ,http://s.ytimg.com/yts/img/watermark/youtube_watermark-vflHX6b6E.png,http://s.ytimg.com/yts/img/watermark/youtube_hd_watermark-vflAzLcD6.png
	showpopout => 1
	is_html5_mobile_device => false
sts => 135835958260
html5 => false
params => 
	bgcolor => #000000
	allowscriptaccess => always
	allowfullscreen => true
url => http://s.ytimg.com/yts/swfbin/watch_as3-vfl97HaY5.swf

## Breakdown of each Stream
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D45%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=hd720&itag=45&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v12.cache3.c.youtube.com&sig=CED402F71D391EAABAFB2AD6C1812AAD6DF0DB03.B7DF7C50CBBCE4A9DBEC8F38F9D647FCE0072A5E
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D45%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1
	FMT_URL: quality=hd720
	FMT_URL: itag=45
	FMT_URL: type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22
	FMT_URL: fallback_host=tc.v12.cache3.c.youtube.com
	FMT_URL: sig=CED402F71D391EAABAFB2AD6C1812AAD6DF0DB03.B7DF7C50CBBCE4A9DBEC8F38F9D647FCE0072A5E
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D22%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=hd720&itag=22&type=video%2Fmp4%3B+codecs%3D%22avc1.64001F%2C+mp4a.40.2%22&fallback_host=tc.v7.cache8.c.youtube.com&sig=0DD8A344C3FC86E7E2EE876A5EA695957343A54F.AE6380142EA60A7E363CC3DB85491E875F7EFD38
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D22%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1
	FMT_URL: quality=hd720
	FMT_URL: itag=22
	FMT_URL: type=video%2Fmp4%3B+codecs%3D%22avc1.64001F%2C+mp4a.40.2%22
	FMT_URL: fallback_host=tc.v7.cache8.c.youtube.com
	FMT_URL: sig=0DD8A344C3FC86E7E2EE876A5EA695957343A54F.AE6380142EA60A7E363CC3DB85491E875F7EFD38
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D44%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=large&itag=44&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v3.cache5.c.youtube.com&sig=525616128DCA0314974E1D204553D160EA700218.0FFF0D63AC7C60C0B4FF3E766167A62FAE3A38A6
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D44%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1
	FMT_URL: quality=large
	FMT_URL: itag=44
	FMT_URL: type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22
	FMT_URL: fallback_host=tc.v3.cache5.c.youtube.com
	FMT_URL: sig=525616128DCA0314974E1D204553D160EA700218.0FFF0D63AC7C60C0B4FF3E766167A62FAE3A38A6
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D35%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=large&itag=35&type=video%2Fx-flv&fallback_host=tc.v18.cache3.c.youtube.com&sig=150C044A4A0BC1D0B4DED33C88B8541CA0C119A4.64502FD633C3B874B6EDB758E81FB1881877B346
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D35%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1
	FMT_URL: quality=large
	FMT_URL: itag=35
	FMT_URL: type=video%2Fx-flv
	FMT_URL: fallback_host=tc.v18.cache3.c.youtube.com
	FMT_URL: sig=150C044A4A0BC1D0B4DED33C88B8541CA0C119A4.64502FD633C3B874B6EDB758E81FB1881877B346
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D43%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=medium&itag=43&type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22&fallback_host=tc.v1.cache8.c.youtube.com&sig=0CDC36220AE13CFEB2F6FF20BA7A84DC2AD8F40D.A54D4E8CAF57131DE397ED597D5B658E08A2B248
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D43%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1
	FMT_URL: quality=medium
	FMT_URL: itag=43
	FMT_URL: type=video%2Fwebm%3B+codecs%3D%22vp8.0%2C+vorbis%22
	FMT_URL: fallback_host=tc.v1.cache8.c.youtube.com
	FMT_URL: sig=0CDC36220AE13CFEB2F6FF20BA7A84DC2AD8F40D.A54D4E8CAF57131DE397ED597D5B658E08A2B248
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D34%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=medium&itag=34&type=video%2Fx-flv&fallback_host=tc.v2.cache3.c.youtube.com&sig=69396E9C184A64653EDA9890B9D34E85431FD42E.88BD0741D16D0984270E7C723F05EEC7C41AE39D
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D34%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1
	FMT_URL: quality=medium
	FMT_URL: itag=34
	FMT_URL: type=video%2Fx-flv
	FMT_URL: fallback_host=tc.v2.cache3.c.youtube.com
	FMT_URL: sig=69396E9C184A64653EDA9890B9D34E85431FD42E.88BD0741D16D0984270E7C723F05EEC7C41AE39D
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D18%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1&quality=medium&itag=18&type=video%2Fmp4%3B+codecs%3D%22avc1.42001E%2C+mp4a.40.2%22&fallback_host=tc.v11.cache6.c.youtube.com&sig=B48054FA25DB1278A794CC20ADED3205B8213E6E.238D5C5FBA9E23975F2DA5678F67500DF0161C51
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26mv%3Dm%26mt%3D1358403493%26ms%3Dau%26ratebypass%3Dyes%26ipbits%3D8%26itag%3D18%26upn%3DFnjsZA5vtsE%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26sparams%3Dcp%252Cid%252Cip%252Cipbits%252Citag%252Cratebypass%252Csource%252Cupn%252Cexpire%26id%3Dc4b8334f415cc57c%26ip%3D66.69.79.212%26key%3Dyt1
	FMT_URL: quality=medium
	FMT_URL: itag=18
	FMT_URL: type=video%2Fmp4%3B+codecs%3D%22avc1.42001E%2C+mp4a.40.2%22
	FMT_URL: fallback_host=tc.v11.cache6.c.youtube.com
	FMT_URL: sig=B48054FA25DB1278A794CC20ADED3205B8213E6E.238D5C5FBA9E23975F2DA5678F67500DF0161C51
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D5%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=5&type=video%2Fx-flv&fallback_host=tc.v7.cache5.c.youtube.com&sig=8FC28D250D437EDC1BA3AAB6AD7BE082981F7199.18AE86F3EABF797EC8D9092D564E331CBC14BBB2
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D5%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1
	FMT_URL: quality=small
	FMT_URL: itag=5
	FMT_URL: type=video%2Fx-flv
	FMT_URL: fallback_host=tc.v7.cache5.c.youtube.com
	FMT_URL: sig=8FC28D250D437EDC1BA3AAB6AD7BE082981F7199.18AE86F3EABF797EC8D9092D564E331CBC14BBB2
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D36%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=36&type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22&fallback_host=tc.v5.cache4.c.youtube.com&sig=50B12E1E27449E9A1055E65DB485A30A1B2E32C2.1890199429A0D9BF45F0D1898A066D6FC58DA9E7
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D36%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1
	FMT_URL: quality=small
	FMT_URL: itag=36
	FMT_URL: type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22
	FMT_URL: fallback_host=tc.v5.cache4.c.youtube.com
	FMT_URL: sig=50B12E1E27449E9A1055E65DB485A30A1B2E32C2.1890199429A0D9BF45F0D1898A066D6FC58DA9E7
URL_STRING: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D17%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1&quality=small&itag=17&type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22&fallback_host=tc.v22.cache2.c.youtube.com&sig=AF7A1A4613A3230109743C9A4E8A3E2C3989B6DD.94054C800BE1CDEA9BD7789AF984B3F35B44DD02
	FMT_URL: url=http%3A%2F%2Fr2---sn-mv-q4fe.c.youtube.com%2Fvideoplayback%3Fnewshard%3Dyes%26ip%3D66.69.79.212%26source%3Dyoutube%26sver%3D3%26expire%3D1358428535%26algorithm%3Dthrottle-factor%26burst%3D40%26fexp%3D927604%252C929113%252C909919%252C916611%252C916709%252C920704%252C912806%252C922403%252C922405%252C929901%252C913605%252C925710%252C929104%252C929110%252C929114%252C908493%252C920201%252C913302%252C919009%252C911116%252C910221%252C901451%26mt%3D1358403493%26ms%3Dau%26factor%3D1.25%26id%3Dc4b8334f415cc57c%26itag%3D17%26cp%3DU0hUTVRNVl9IT0NONF9MR1pGOnlqWllkMl85Y2oz%26mv%3Dm%26sparams%3Dalgorithm%252Cburst%252Ccp%252Cfactor%252Cid%252Cip%252Cipbits%252Citag%252Csource%252Cupn%252Cexpire%26ipbits%3D8%26upn%3DFnjsZA5vtsE%26key%3Dyt1
	FMT_URL: quality=small
	FMT_URL: itag=17
	FMT_URL: type=video%2F3gpp%3B+codecs%3D%22mp4v.20.3%2C+mp4a.40.2%22
	FMT_URL: fallback_host=tc.v22.cache2.c.youtube.com
	FMT_URL: sig=AF7A1A4613A3230109743C9A4E8A3E2C3989B6DD.94054C800BE1CDEA9BD7789AF984B3F35B44DD02