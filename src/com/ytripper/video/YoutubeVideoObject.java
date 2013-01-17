/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.video;

import com.ytripper.net.YoutubeVideoStream;
import java.util.HashMap;

/**
 *
 * @author mmain
 */
public class YoutubeVideoObject {
    protected String title;
    protected YoutubeVideoStreamStore streams = new YoutubeVideoStreamStore();
    protected HashMap<String, Object> videoInfo;
    
    public YoutubeVideoObject() {}
    
    public YoutubeVideoObject(HashMap<String, Object> videoInfo) {
        this.videoInfo = videoInfo;
        this.title = (String)((HashMap<String, Object>)videoInfo.get("args")).get("title");
    }
    
    public YoutubeVideoStream getStream(Integer quality, String codec) {
        YoutubeVideoStreamStore closest = streams.findHighestResolution(quality).findByCodec(codec);
        return closest.getOne();
    }
    
    public boolean downloadVideoStream(Integer quality, String codec, String downloadDirectory) {
        YoutubeVideoStreamStore closest = streams.findHighestResolution(quality).findByCodec(codec);
        
        try {
            YoutubeVideoStream closestStream = closest.getOne();
            if (closestStream != null) {
                return closestStream.writeToFile(downloadDirectory, title);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            return false;
        }
    }
    
    public boolean downloadVideoStream(Integer quality, String codec, String downloadDirectory, String filename) {
        YoutubeVideoStreamStore closest = streams.findHighestResolution(quality).findByCodec(codec);
        
        try {
            YoutubeVideoStream closestStream = closest.getOne();
            if (closestStream != null) {
                return closestStream.writeToFile(downloadDirectory, filename);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            return false;
        }
    }
    
    public void addStream(YoutubeVideoStream stream) {
        stream.setTitle(title);
        streams.add(stream);
    }
    
    public String getTitle() {
        return title;
    }
    
    public YoutubeVideoStreamStore getStreams() {
        return streams;
    }
    
    public Object getParam(String param) {
        return ((HashMap<String, Object>)videoInfo.get("params")).get(param);
    }
    
    public Object getArg(String arg) {
        return ((HashMap<String, Object>)videoInfo.get("args")).get(arg);
    }
    
    public Object getAsset(String asset) {
        return ((HashMap<String, Object>)videoInfo.get("assets")).get(asset);
    }
    
    public Object getAtr(String attr) {
        return ((HashMap<String, Object>)videoInfo.get("attrs")).get(attr);
    }
    
    public Object get(String key) {
        return videoInfo.get(key);
    }
}
