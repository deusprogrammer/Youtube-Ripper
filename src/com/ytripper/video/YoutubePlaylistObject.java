/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.video;

import java.util.ArrayList;

/**
 *
 * @author mmain
 */
public class YoutubePlaylistObject {
    protected String title = "Unnamed";
    protected ArrayList<YoutubeVideoObject> youtubeVideos = new ArrayList<YoutubeVideoObject>();
    
    public YoutubePlaylistObject() {}
    
    public YoutubePlaylistObject(String title) {
        this.title = title;
    }
    
    public void addYoutubeVideoObject(YoutubeVideoObject youtubeVideo) {
        youtubeVideos.add(youtubeVideo);
    }
    
    public ArrayList<YoutubeVideoObject> getYoutubeVideoObjects() {
        return youtubeVideos;
    }
    
    public boolean downloadYoutubeVideoStreams(Integer quality, String codec, String downloadDirectory) {
        return false;
    }
    
    public boolean downloadYoutubeVideoStreams(Integer quality, String codec, String downloadDirectory, String filenamePrefix) {
        return false;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getSafeTitle() {
        return title.replace(" ", "_").replace("\\", "").replace("/", "").replace(">", "").replace("<", "").replace("\"", "").replace("|", "").replace("?", "").replace("*", "").replace(":", "");
    }
   
}