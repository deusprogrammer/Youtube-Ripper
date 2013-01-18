/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper;

import com.ytripper.video.YoutubeVideoObject;
import com.ytripper.net.YoutubeConnection;
import com.ytripper.thread.YoutubeDownloadJob;
import com.ytripper.thread.YoutubeDownloadThread;
import com.ytripper.thread.YoutubeDownloadThreadPool;
import com.ytripper.video.YoutubePlaylistObject;
import java.io.IOException;

/**
 *
 * @author mmain
 */
public class YoutubeRipper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        YoutubeRipperFrame frame = new YoutubeRipperFrame();
        
        frame.setVisible(true);
    }
}
