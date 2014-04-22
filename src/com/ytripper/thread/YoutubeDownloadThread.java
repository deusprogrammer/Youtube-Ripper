/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mmain
 */
public class YoutubeDownloadThread extends Thread {
    
    protected boolean running = true;
    protected YoutubeDownloadThreadPool pool;

    public YoutubeDownloadThread() {}
    
    public YoutubeDownloadThread(YoutubeDownloadThreadPool pool) {
        this.pool = pool;
    }
    
    public void kill() {
        running = false;
    }
    
    public void run() {
        while (running) {
            YoutubeDownloadJob job;
            
            while ((job = pool.getJob()) == null) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(YoutubeDownloadThread.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                
                if (!running) {
                    return;
                }
            }
            
            job.startDownload();
            
            pool.addCompleted(job);
        }
    }
}
