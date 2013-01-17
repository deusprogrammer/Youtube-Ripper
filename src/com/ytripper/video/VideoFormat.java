/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.video;

/**
 *
 * @author mmain
 */
public class VideoFormat {
    protected int resolution;
    protected String codec;
    
    public String toString() {
        return resolution + "p (" + codec + ")";
    }
    
    public VideoFormat(int itag) {
        switch(itag) {
            case 46:
            case 45:
            case 44:
            case 43:
            case 5:
                codec = "flv";
                break;
            case 37:
            case 22:
            case 35:
            case 18:
            case 34:
            case 36:
            case 17:
                codec = "mp4";
                break;
        }
        
        switch(itag) {
            case 37:
            case 46:
                resolution = 1080;
                break;
            case 22:
            case 45:
                resolution = 720;
                break;
            case 35:
            case 44:
                resolution = 480;
                break;
            case 34:
            case 43:
                resolution = 360;
                break;
            case 36:
            case 5:
                resolution = 240;
                break;
            case 17:
                resolution = 114;
                break;
        }
    }
    
    public int getResolution() {
        return resolution;
    }
    
    public String getCodec() {
        return codec;
    }
}
