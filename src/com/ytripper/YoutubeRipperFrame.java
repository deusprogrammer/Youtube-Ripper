/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper;

import com.ytripper.net.YoutubeConnection;
import com.ytripper.thread.YoutubeDownloadJob;
import com.ytripper.thread.YoutubeDownloadThreadPool;
import com.ytripper.video.YoutubePlaylistObject;
import com.ytripper.video.YoutubeVideoObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author mmain
 */
public class YoutubeRipperFrame extends javax.swing.JFrame {
    protected YoutubeDownloadThreadPool pool = new YoutubeDownloadThreadPool();
    protected ArrayList<YoutubeDownloadJob> runningTasks = new ArrayList<>();
    
    protected Timer timer;
    
    protected String oldDirectory;
    
    /**
     * Creates new form YoutubeRipperFrame
     */
    public YoutubeRipperFrame() {
        initComponents();
        
        this.downloadDirectoryField.setText(YoutubeRipperConfig.getDownloadDirectory());
        
        timer = new Timer(40, new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Collections.sort(runningTasks);
                runningTasksList.setListData(runningTasks.toArray());
            }
        });
        
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        youtubeUrl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        playlistId = new javax.swing.JTextField();
        downloadOneButton = new javax.swing.JButton();
        downloadPlaylistButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        runningTasksList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        downloadDirectoryField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        verifyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deus the Ripper (YouTube Ripping Tool)");

        jLabel1.setText("Youtube URL");

        jLabel2.setText("Youtube Playlist ID");

        downloadOneButton.setText("Download Video");
        downloadOneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadOneButtonActionPerformed(evt);
            }
        });

        downloadPlaylistButton.setText("Download Playlist");
        downloadPlaylistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadPlaylistButtonActionPerformed(evt);
            }
        });

        runningTasksList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                runningTasksListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(runningTasksList);

        jLabel3.setText("Running Tasks:");

        jLabel4.setText("Download Directory");

        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        verifyButton.setText("Verify");
        verifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1))
                                        .addGap(30, 30, 30)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(downloadDirectoryField)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(verifyButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(saveButton))
                                    .addComponent(youtubeUrl)
                                    .addComponent(playlistId, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(downloadPlaylistButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(downloadOneButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(youtubeUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(downloadOneButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(playlistId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downloadPlaylistButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadDirectoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton)
                    .addComponent(verifyButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadOneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadOneButtonActionPerformed
        YoutubeVideoObject video = YoutubeConnection.getYoutubeVideoObject(youtubeUrl.getText());
        
        String downloadDirectory = downloadDirectoryField.getText();
        
        YoutubeDownloadJob job = new YoutubeDownloadJob(video, 720, "mp4", downloadDirectory + "/singles");
        pool.addJob(job);
        runningTasks.add(job);
        
        youtubeUrl.setText("");
        Collections.sort(runningTasks);
        runningTasksList.setListData(runningTasks.toArray());
    }//GEN-LAST:event_downloadOneButtonActionPerformed

    private void downloadPlaylistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadPlaylistButtonActionPerformed
        YoutubePlaylistObject playlist = YoutubeConnection.getYoutubePlaylistObject(playlistId.getText());
        
        String downloadDirectory = downloadDirectoryField.getText();
        
        for (YoutubeVideoObject video : playlist.getYoutubeVideoObjects()) {
            YoutubeDownloadJob job = new YoutubeDownloadJob(video, 720, "mp4", downloadDirectory + "/" + playlist.getSafeTitle());
            pool.addJob(job);
            runningTasks.add(job);
        }
        
        playlistId.setText("");
        Collections.sort(runningTasks);
        runningTasksList.setListData(runningTasks.toArray());
    }//GEN-LAST:event_downloadPlaylistButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        YoutubeRipperConfig.setDownloadDirectory(downloadDirectoryField.getText());
        YoutubeRipperConfig.save();
        oldDirectory = downloadDirectoryField.getText();
        saveButton.setEnabled(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void runningTasksListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_runningTasksListValueChanged
        if (runningTasksList.getSelectedValue() != null) {
            YoutubeDownloadJob job = (YoutubeDownloadJob)runningTasksList.getSelectedValue();
            System.out.println("LIST SELECTION CHANGED: " + job.getMessage());
            JOptionPane.showMessageDialog(null, "UUID: " + job.getUuid() + "\nMESSAGE: " + job.getMessage());
        }
    }//GEN-LAST:event_runningTasksListValueChanged

    private void verifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyButtonActionPerformed
        File directory = new File(downloadDirectoryField.getText());
        if (directory.exists()) {
            saveButton.setEnabled(true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Directory doesn't exist!");
        }
    }//GEN-LAST:event_verifyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField downloadDirectoryField;
    private javax.swing.JButton downloadOneButton;
    private javax.swing.JButton downloadPlaylistButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField playlistId;
    private javax.swing.JList runningTasksList;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton verifyButton;
    private javax.swing.JTextField youtubeUrl;
    // End of variables declaration//GEN-END:variables
}
