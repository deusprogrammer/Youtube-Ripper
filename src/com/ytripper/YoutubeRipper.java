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
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        /*
        YoutubeVideoObject youtubeVideo = YoutubeConnection.getYoutubeVideoObject("http://www.youtube.com/watch?v=4ZLsV_B8X5w");
        if (youtubeVideo != null) {
            youtubeVideo.downloadVideoStream(720, "mp4", "E:\\Media\\Youtube");
        }
        */
        
        //Get playlist object
        YoutubePlaylistObject playlist = YoutubeConnection.getYoutubePlaylistObject("PL3A352D61283EB216");
        
        //Create a new thread pool
        YoutubeDownloadThreadPool pool = new YoutubeDownloadThreadPool();
        
        Integer completedJobs = 0;
        Integer nJobs = playlist.getYoutubeVideoObjects().size();
        
        //Pass jobs to thread pool
        for (YoutubeVideoObject youtubeVideo : playlist.getYoutubeVideoObjects()) {
            String uuid = pool.addJob(youtubeVideo, 720, "mp4", "E:/Media/Youtube/" + playlist.getSafeTitle());
            System.out.println("Created job: " + uuid);
        }
        
        //Wait for jobs to complete
        while (completedJobs < nJobs) {
            YoutubeDownloadJob job = pool.getCompleted();
            
            if (job != null) {
                completedJobs++;
                System.out.println("Completed job " + job.getUuid());
                System.out.println("Success:      " + (job.getSuccess() ? "Yes" : "No") + "\n");
            }
        }
        
        System.out.println("All jobs complete!");
    }
}
