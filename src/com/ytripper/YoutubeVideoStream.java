/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper;

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
    VideoFormat format;
    String url;
    String title;
    
    protected static HashMap<String, Object> parseQueryString(String url) {
        if (url.indexOf("?") == -1) {
            return null;
        }
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        String queryString = url.substring(url.indexOf("?") + 1);
        String[] elements = queryString.split("&");
        
        for (String element : elements) {
            String[] kvm = element.split("=", 2);
            map.put(kvm[0], kvm[1]);
        }
        
        return map;
    }
    
    public String toString() {
        return "URL:    " + url + "\n" + "FORMAT: " + format;
    }
    
    public VideoFormat getFormat() {
        return format;
    }
    
    public YoutubeVideoStream(String url, String quality, String signature, String title) {
        this.url = url + "&signature=" + signature;
        this.title = title;
        
        HashMap<String, Object> queryString = parseQueryString(url);

        if (queryString != null) {
            int itag = Integer.parseInt(quality);
            this.format = new VideoFormat(itag);
        }
    }
    
    public boolean writeToFile(String downloadDirectory) throws IOException {
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
                
        title = title.replace(" ", "_").replace("(", "_").replace(")", "_").replace("/", "_").replace("!", "").replace("?", "") + "." + format.codec;
        FileOutputStream out;
        System.out.println("TITLE: " + title);

        try {
            out = new FileOutputStream(new File(downloadDirectory + "/" + title));
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
        return ((YoutubeVideoStream)stream).getFormat().resolution - this.getFormat().resolution;
    }
}
