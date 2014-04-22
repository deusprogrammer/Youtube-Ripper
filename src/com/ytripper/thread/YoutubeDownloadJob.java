/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.thread;

import com.ytripper.net.YoutubeVideoStream;
import com.ytripper.video.YoutubeVideoObject;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    protected boolean success = true;
    
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
            System.out.println("CODEC IS ==> " + videoStream.getFormat().getCodec());
            success = videoStream.writeToFile(downloadDirectory, videoObject.getSafeTitle());
            if (success && videoStream.getFormat().getCodec().equals("mp4")) {
                videoStream.convertToMp3(downloadDirectory, videoObject.getSafeTitle());
            }
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
    
    public String getMessage() {
        return videoStream.getMessage();
    }
    
    public String toString() {
        String ret = videoObject.getTitle() + " ( " + String.format("%.2f", videoStream.getPercentDone()) + "% )";
        if (!success) {
            ret += "[FAILED]";
        } else if (success && getPercentDone() >= 100.0) {
            ret += "[SUCCESS]";
        }
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        return ((YoutubeDownloadJob)o).getPercentDone().intValue() - this.getPercentDone().intValue();
    }
}
