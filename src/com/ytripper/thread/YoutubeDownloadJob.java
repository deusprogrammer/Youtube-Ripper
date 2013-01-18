/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.thread;

import com.ytripper.net.YoutubeVideoStream;
import com.ytripper.video.YoutubeVideoObject;
import java.util.UUID;

/**
 *
 * @author mmain
 */
public class YoutubeDownloadJob implements Comparable {
    protected YoutubeVideoObject videoObject;
    protected YoutubeVideoStream videoStream;
    
    protected Integer requestedQuality;
    protected String requestedCodec;
    protected String downloadDirectory;
    
    protected String uuid = UUID.randomUUID().toString();
    protected boolean success;
    
    public YoutubeDownloadJob() {}
    
    public YoutubeDownloadJob(YoutubeVideoObject videoObject, Integer quality, String codec, String downloadDirectory) {
        this.videoObject = videoObject;
        this.requestedQuality = quality;
        this.requestedCodec = codec;
        this.downloadDirectory = downloadDirectory;
        
        this.videoStream = videoObject.getStream(quality, codec);
    }
    
    public void startDownload() {
        if (videoStream != null) {
            success = videoStream.writeToFile(downloadDirectory, videoObject.getSafeTitle());
        }
        else {
            System.out.println("***STREAM IS NULL!");
            success = false;
        }
    }
    
    public boolean getSuccess() {
        return success;
    }
    
    public Double getPercentDone() {
        return videoStream.getPercentDone();
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String toString() {
        return videoObject.getTitle() + " ( " + String.format("%.2f", videoStream.getPercentDone()) + "% )";
    }

    @Override
    public int compareTo(Object o) {
        return ((YoutubeDownloadJob)o).getPercentDone().intValue() - this.getPercentDone().intValue();
    }
}
