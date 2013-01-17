/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.thread;

import com.ytripper.video.YoutubeVideoObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author mmain
 */
public class YoutubeDownloadThreadPool {
    protected Integer nWorkers = 3;
    ArrayList<YoutubeDownloadThread> workers = new ArrayList<YoutubeDownloadThread>();
    Queue<YoutubeDownloadJob> jobs = new LinkedList<YoutubeDownloadJob>();
    Queue<YoutubeDownloadJob> completed = new LinkedList<YoutubeDownloadJob>();
    
    public YoutubeDownloadThreadPool() {
        for (int i=0; i < nWorkers; i++) {
            YoutubeDownloadThread worker = new YoutubeDownloadThread(this);
            worker.start();
            workers.add(worker);
        }
    }
    
    public YoutubeDownloadThreadPool(int nWorkers) {
        this.nWorkers = nWorkers;
        
        for (int i=0; i < nWorkers; i++) {
            YoutubeDownloadThread worker = new YoutubeDownloadThread(this);
            worker.start();
            workers.add(worker);
        }
    }
    
    public void killWorkers() {
        for (YoutubeDownloadThread worker: workers) {
            worker.kill();
        }
    }
    
    public synchronized String addJob(YoutubeVideoObject youtubeVideo, Integer quality, String codec, String downloadDirectory) {
        YoutubeDownloadJob job = new YoutubeDownloadJob(youtubeVideo, quality, codec, downloadDirectory);
        jobs.add(job);
        return job.getUuid();
    }
    
    public synchronized YoutubeDownloadJob getJob() {
        return jobs.poll();
    }
    
    public synchronized void addCompleted(YoutubeDownloadJob job) {
        completed.add(job);
    }
    
    public synchronized YoutubeDownloadJob getCompleted() {
        return completed.poll();
    }
}
