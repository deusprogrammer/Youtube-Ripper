package com.ytripper.thread;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;

/**
 *
 * @author mmain
 */
public class YoutubeClipboard implements ClipboardOwner {
    public void setClipboardContents( String aString ){
      StringSelection stringSelection = new StringSelection( aString );
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents( stringSelection, this );
    }

    public String getClipboardContents() {
      String result = "";
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      //odd: the Object param of getContents is not currently used
      Transferable contents = clipboard.getContents(null);
      boolean hasTransferableText =
        (contents != null) &&
        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
      ;
      if ( hasTransferableText ) {
        try {
          result = (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        catch (UnsupportedFlavorException ex){
          //highly unlikely since we are using a standard DataFlavor
          System.out.println(ex);
          ex.printStackTrace();
        }
        catch (IOException ex) {
          System.out.println(ex);
          ex.printStackTrace();
        }
      }
      return result;
    }    

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}