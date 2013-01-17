/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.net;

import java.io.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

/**
 *
 * @author mmain
 */
public class YoutubeStreamResponseHandler implements ResponseHandler {

    @Override
    public Object handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        System.out.println("IN STREAM RESPONSE HANDLER!");
        
        System.out.println("CONTENT-TYPE: " + hr.getFirstHeader("Content-Type").getValue());
        System.out.println("STATUS:       " + hr.getStatusLine());

        if (hr.getFirstHeader("Content-Type").getValue().matches("video/.*")) {
            System.out.println("VIDEO");
            return hr.getEntity().getContent();
        }
        else {
            System.out.println("HTML");
            return null;
        }
    }
    
}
