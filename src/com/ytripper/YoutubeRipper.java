/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper;

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
        YoutubeConnection.downloadVideoStream("http://www.youtube.com/watch?v=obAiDec2m3k", 720, "mp4", "E:\\Media\\Youtube");
    }
}
