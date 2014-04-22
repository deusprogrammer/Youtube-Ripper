/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.net;

import com.ytripper.thread.YoutubeDownloadJob;
import com.ytripper.util.StringUtil;
import com.ytripper.video.VideoFormat;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.MultimediaInfo;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
    protected Double percentDone = 0.0;
    
    protected String title;
    protected String message = null;
    protected String filePath = null;
    
    protected HashMap<String, String> fmtMap;
   
    @Override
    public String toString() {
        return "URL:    " + url + "\n" + "FORMAT: " + format;
    }
    
    public VideoFormat getFormat() {
        return format;
    }
    
    public String getMessage() {
        return message;
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
    
    public boolean hasFormat() {
        return format != null;
    }
    
    public boolean hasUrl() {
        return url != null;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public boolean writeToFile(String downloadDirectory, String filename) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse hr;
        HttpHost target = new HttpHost(StringUtil.getHost(url), 80, "http");
        FileOutputStream out;
        
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);

        try {
            System.out.println("Executing request " + url);
            System.out.println("HOST:  " + StringUtil.getHost(url));
            System.out.println("URI:   " + StringUtil.getURI(url));
            System.out.println("QUERY: " + StringUtil.getQuery(url));
            HttpGet httpget = new HttpGet(StringUtil.getURI(url) + "?" + StringUtil.getQuery(url));

            hr = httpclient.execute(target, httpget);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            message = e.getMessage();
            httpclient.getConnectionManager().shutdown();
            return false;
        }
        
        System.out.println("CONTENT-TYPE: " + hr.getFirstHeader("Content-Type").getValue());
        System.out.println("STATUS:       " + hr.getStatusLine());

        if (!hr.getFirstHeader("Content-Type").getValue().matches("video/.*")) {
            return false;
        }
        
        //Clean up directory name and create it if it doesn't exist
        downloadDirectory = downloadDirectory.replace(" ", "_").replace("(", "_").replace(")", "_").replace("!", "").replace("?", "");
        new File(downloadDirectory).mkdirs();
        
        //Clean up filename
        filename = filename.replace(" ", "_").replace("(", "_").replace(")", "_").replace("/", "_").replace("!", "").replace("?", "") + "." + format.getCodec();
        
        //Create full path
        filePath = downloadDirectory + "/" + filename;
        System.out.println("PATH: " + filePath);

        try {
            out = new FileOutputStream(new File(filePath));
            BufferedInputStream in = new BufferedInputStream(hr.getEntity().getContent());
            byte[] bytes = new byte[4096];
            int bytesRead = 0;
            int bytesMax = Integer.parseInt(hr.getFirstHeader("Content-Length").getValue());
            int totalBytesRead = 0;
            while ((bytesRead = in.read(bytes)) > 0) {
                out.write(bytes, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentDone = ((double)totalBytesRead/(double)bytesMax)*100.0;
            }
            out.close();
            message = "Job complete!";
            return true;
         } catch (IOException | IllegalStateException | NumberFormatException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            message = e.getMessage();
            return false;
        }
    }
    
    public void convertToMp3(String downloadDirectory, String filename) {
        //Clean up directory name and create it if it doesn't exist
        downloadDirectory = downloadDirectory.replace(" ", "_").replace("(", "_").replace(")", "_").replace("!", "").replace("?", "");
        
        //Clean up filename
        String targetPath = filename.replace(" ", "_").replace("(", "_").replace(")", "_").replace("/", "_").replace("!", "").replace("?", "") + ".mp3";
        
        //Create full path
        targetPath = downloadDirectory + "/" + targetPath;
        
        System.out.println("SOURCE FOR MP3: " + filePath);
        System.out.println("TARGET FOR MP3: " + targetPath);
        
        File source = new File(filePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        
        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(YoutubeDownloadJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncoderException ex) {
            Logger.getLogger(YoutubeDownloadJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Double getPercentDone() {
        return percentDone;
    }

    @Override
    public int compareTo(Object stream) {
        return ((YoutubeVideoStream)stream).getFormat().getResolution() - this.getFormat().getResolution();
    }
}