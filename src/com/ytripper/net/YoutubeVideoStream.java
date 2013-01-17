/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.net;

import com.ytripper.video.VideoFormat;
import com.ytripper.util.StringUtil;

import java.io.*;
import java.util.HashMap;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author mmain
 */
public class YoutubeVideoStream implements Comparable {
    protected VideoFormat format;
    protected String url;
    
    protected HashMap<String, String> fmtMap;
   
    public String toString() {
        return "URL:    " + url + "\n" + "FORMAT: " + format;
    }
    
    public VideoFormat getFormat() {
        return format;
    }
    
    public HashMap<String, String> getFormatMap() {
        return fmtMap;
    }
    
    public YoutubeVideoStream(HashMap<String, String> fmtMap) {
        this.fmtMap = fmtMap;
        url = fmtMap.get("url").replaceFirst("http%3A%2F%2F", "http://").replaceAll("%3F","?").replaceAll("%2F", "/").replaceAll("%3D","=").replaceAll("%26", "&").replaceAll("\\u0026", "&").replaceAll("%252C", "%2C");
        url = url + "&signature=" + fmtMap.get("sig");

        int itag = Integer.parseInt(fmtMap.get("itag"));
        this.format = new VideoFormat(itag);
    }
    
    public boolean writeToFile(String downloadDirectory, String filename) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse hr;
        HttpHost target = new HttpHost(StringUtil.getHost(url), 80, "http");
        InputStream stream;
        
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);

        try {
            HttpGet httpget = new HttpGet(StringUtil.getURI(url));

            System.out.println("Executing request " + url);

            hr = httpclient.execute(target, httpget);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            httpclient.getConnectionManager().shutdown();
            return false;
        }
        
        System.out.println("CONTENT-TYPE: " + hr.getFirstHeader("Content-Type").getValue());
        System.out.println("STATUS:       " + hr.getStatusLine());

        if (!hr.getFirstHeader("Content-Type").getValue().matches("video/.*")) {
            return false;
        }
                
        filename = filename.replace(" ", "_").replace("(", "_").replace(")", "_").replace("/", "_").replace("!", "").replace("?", "") + "." + format.getCodec();
        FileOutputStream out;
        System.out.println("TITLE: " + filename);

        try {
            out = new FileOutputStream(new File(downloadDirectory + "/" + filename));
            BufferedInputStream in = new BufferedInputStream(hr.getEntity().getContent());
            byte[] bytes = new byte[4096];
            int bytesRead = 0;
            int bytesMax = Integer.parseInt(hr.getFirstHeader("Content-Length").getValue());
            int totalBytesRead = 0;
            while ((bytesRead = in.read(bytes)) > 0) {
                out.write(bytes, 0, bytesRead);
                totalBytesRead += bytesRead;
                System.out.println("BYTES READ: " + totalBytesRead);
                System.out.println("PERCENT: " + ((double)totalBytesRead/(double)bytesMax)*100.0);
            }
            return true;
         } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

    public int compareTo(Object stream) {
        return ((YoutubeVideoStream)stream).getFormat().getResolution() - this.getFormat().getResolution();
    }
}