/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytripper.YoutubeVideoStream;
import com.ytripper.YoutubeVideoStreamStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;


/**
 *
 * @author mmain
 */
public class YoutubeResponseHandler implements ResponseHandler {
    protected static ObjectMapper mapper = new ObjectMapper();
    
    protected static void printHashMap(HashMap<String, Object> map, int level) {
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof HashMap) {
                System.out.println(entry.getKey() + " => ");
                printHashMap((HashMap<String, Object>)entry.getValue(), level + 1);
            }
            else {
                for(int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                System.out.println(entry.getKey() + " => " + entry.getValue());
                
            }
        }
    }

    protected YoutubeVideoStreamStore handleHtml(InputStream content) throws IOException {
        YoutubeVideoStreamStore streams = new YoutubeVideoStreamStore();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        String line;

        while ((line = in.readLine()) != null) {
            if (line.matches("(.*)\"url_encoded_fmt_stream_map\":(.*)")) {
                line = line.substring(line.indexOf("=") + 1).trim();
                HashMap<String, Object> jsonData = mapper.readValue(line, HashMap.class);
                printHashMap(jsonData, 0);
                String title = ((HashMap<String,String>)jsonData.get("args")).get("title");
                String fmtString = ((HashMap<String,String>)jsonData.get("args")).get("url_encoded_fmt_stream_map");
                String[] urlStrings = fmtString.split(",");
                
                for(String urlString : urlStrings) {
                    System.out.println("URL_STRING: " + urlString);
                    try {
                        HashMap<String, String> fmtMap = new HashMap<String, String>();
                        //String[] fmtUrlPair = urlString.split("&*url=");
                        String[] splitFmt = urlString.split("&");
                        
                        for (String fmtEntry : splitFmt) {
                            if (fmtEntry.matches("url=.*")) {
                                fmtMap.put("url", fmtEntry.substring(fmtEntry.indexOf("=") + 1));
                            } else if (fmtEntry.matches("itag=.*")) {
                                fmtMap.put("itag", fmtEntry.substring(fmtEntry.indexOf("=") + 1));
                            } else if (fmtEntry.matches("sig=.*")) {
                                fmtMap.put("sig", fmtEntry.substring(fmtEntry.indexOf("=") + 1));
                            }

                            System.out.println("\tFMT_URL: " + fmtEntry);
                        }
                        
                        String url       = fmtMap.get("url").replaceFirst("http%3A%2F%2F", "http://").replaceAll("%3F","?").replaceAll("%2F", "/").replaceAll("%3D","=").replaceAll("%26", "&").replaceAll("\\u0026", "&").replaceAll("%252C", "%2C");
                        String itag      = fmtMap.get("itag");
                        String signature = fmtMap.get("sig");

                        streams.add(new YoutubeVideoStream(url, itag, signature, title));
                    } catch (Exception e) {
                        System.out.println("EXCEPTION: " + e.getMessage());
                    }
                }
            }
        }
        
        return streams;
    }
    
    @Override
    public YoutubeVideoStreamStore handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        System.out.println("IN RESPONSE HANDLER!");
        
        System.out.println("CONTENT-TYPE: " + hr.getFirstHeader("Content-Type").getValue());

        if (hr.getFirstHeader("Content-Type").getValue().matches("text/html.*")) {
            System.out.println("HTML");
            return handleHtml(hr.getEntity().getContent());
        }
        else {
            System.out.println(hr.getEntity().getContent().toString());
            return null;
        }
    }
}
