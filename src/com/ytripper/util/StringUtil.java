/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 *
 * @author mmain
 */
public class StringUtil {
    // something like [http://][www.]youtube.[cc|to|pl|ev|do|ma|in]/watch?v=0123456789A 
    public static final String szYTREGEX = "^((H|h)(T|t)(T|t)(P|p)://)?((W|w)(W|w)(W|w)\\.)?(Y|y)(O|o)(U|u)(T|t)(U|u)(B|b)(E|e)\\..{2,5}/(W|w)(A|a)(T|t)(C|c)(H|h)\\?(v|V)=[^&]{11}"; // http://de.wikipedia.org/wiki/CcTLD
    // something like [http://][*].youtube.[cc|to|pl|ev|do|ma|in]/   the last / is for marking the end of host, it does not belong to the hostpart
    public static final String szYTHOSTREGEX = "^((H|h)(T|t)(T|t)(P|p)://)?(.*)\\.(Y|y)(O|o)(U|u)(T|t)(U|u)(B|b)(E|e)\\..{2,5}/";

    // RFC-1123 ? hostname [with protocol]	
    //public static final String szPROXYREGEX = "^((H|h)(T|t)(T|t)(P|p)://)?([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*$";
    public static final String szPROXYREGEX = "(^((H|h)(T|t)(T|t)(P|p)://)?([a-zA-Z0-9]+:[a-zA-Z0-9]+@)?([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\.([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*(:[0-90-90-90-9]{1,4})?$)|()";

    private static final String szPLAYLISTREGEX = "/view_play_list\\?p=([A-Za-z0-9]*)&playnext=[0-9]{1,2}&v=";
    
    public static boolean isJSONValid(String test) {
        ObjectMapper mapper = new ObjectMapper();
        try {
             mapper.readValue(test, HashMap.class);
            return true;
        } catch(Exception ex) { 
            return false;
        }
    }
    
    public static String getURI(String url) {
        URL aURL = null;
        try {
            aURL = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (aURL == null) {
            return "";
        }
        
        return(aURL.getPath());
    }

    public static String getHost(String url) {
        URL aURL = null;
        try {
            aURL = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (aURL == null) {
            return "";
        }
        
        return(aURL.getHost());
    }
    
    public static String getQuery(String url) {
        URL aURL = null;
        try {
            aURL = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (aURL == null) {
            return "";
        }
        
        return(aURL.getQuery());
    }
    
    public static String slurp (InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
