/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.video;

import com.ytripper.net.YoutubeVideoStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mmain
 */
public class YoutubeVideoStreamStore {
    protected ArrayList<YoutubeVideoStream> streams = new ArrayList<YoutubeVideoStream>();
    
    public YoutubeVideoStreamStore() {
    }
    
    public void add(YoutubeVideoStream stream) {
        System.out.println("ADDING STREAM!");
        if (stream.getFormat() != null) {
            streams.add(stream);
            Collections.sort(streams);
        }
    }
    
    public YoutubeVideoStreamStore findByResolution(int resolution) {
        YoutubeVideoStreamStore found = new YoutubeVideoStreamStore();
        for (YoutubeVideoStream stream : streams) {
            if (stream.getFormat().getResolution() == resolution) {
                found.add(stream);
            }
        }
        
        return found;
    }
    
    public YoutubeVideoStreamStore findHighestResolution(int resolution) {
        YoutubeVideoStreamStore found = new YoutubeVideoStreamStore();
        int highestResolution = 0;
        
        for (YoutubeVideoStream stream : streams) {
            if (stream.getFormat().getResolution() <= resolution && stream.getFormat().getResolution() > highestResolution) {
                highestResolution = stream.getFormat().getResolution();
            }
        }
        
        for (YoutubeVideoStream stream : streams) {
            if (stream.getFormat().getResolution() == highestResolution) {
                found.add(stream);
            }
        }
        
        return found;
    }
    
    public YoutubeVideoStreamStore findByCodec(String codec) {
        YoutubeVideoStreamStore found = new YoutubeVideoStreamStore();
        for (YoutubeVideoStream stream : streams) {
            if (stream.getFormat().getCodec().equals(codec)) {
                found.add(stream);
            }
        }
        
        if (found.streams.size() == 0) {
            return this;
        }
        
        return found;
    }
    
    public YoutubeVideoStream getOne() {
        for (YoutubeVideoStream stream : streams) {
            System.out.println("STREAM: " + stream);
        }
        
        if (streams.size() >= 1) {
            return streams.get(0);
        }
        else {
            return null;
        }
    }
}
