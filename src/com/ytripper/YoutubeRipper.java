/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper;

import com.ytripper.video.YoutubeVideoObject;
import com.ytripper.net.YoutubeConnection;
import java.io.IOException;

/**
 *
 * @author mmain
 */
public class YoutubeRipper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        YoutubeVideoObject youtubeVideo = YoutubeConnection.getYoutubeVideoObject("http://www.youtube.com/watch?v=4ZLsV_B8X5w");
        if (youtubeVideo != null) {
            youtubeVideo.downloadVideoStream(720, "mp4", "E:\\Media\\Youtube");
        }
    }
}
