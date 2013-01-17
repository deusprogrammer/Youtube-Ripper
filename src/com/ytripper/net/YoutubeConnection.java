package com.ytripper.net;

import com.ytripper.YoutubeVideoStream;
import com.ytripper.YoutubeVideoStreamStore;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class YoutubeConnection {
    
    public static int nVideos = 0;

    public static boolean downloadVideoStream(String url, int desiredResolution, String desiredCodec, String downloadDirectory) throws IOException {
        YoutubeVideoStreamStore streams = new YoutubeVideoStreamStore();
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
        
        try {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getURI());

            ResponseHandler<YoutubeVideoStreamStore> responseHandler = new YoutubeResponseHandler();
            streams = httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            httpclient.getConnectionManager().shutdown();
            return false;
        }
        
        YoutubeVideoStreamStore closest = streams.findHighestResolution(desiredResolution).findByCodec(desiredCodec);
        
        YoutubeVideoStream closestStream = closest.getOne();
        if (closestStream != null) {
            closestStream.writeToFile(downloadDirectory);
        } else {
            return false;
        }
        
        httpclient.getConnectionManager().shutdown();
        return true;
    }
}