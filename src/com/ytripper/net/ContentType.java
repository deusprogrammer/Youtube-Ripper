/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.net;

/**
 *
 * @author mmain
 */
public enum ContentType {
    VIDEO   ("Video", "video/*"),
    TEXT    ("Text", "text/video"),
    DEFAULT ("Default", "*/*");
    
    String name = "";
    String mimeType = "";
    
    ContentType(String name, String mimeType) {
        this.name = name;
        this.mimeType = mimeType;
    }
}
