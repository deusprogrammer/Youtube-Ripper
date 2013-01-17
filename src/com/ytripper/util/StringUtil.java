/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ytripper.util;

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
    
    public static String getURI(String sURL) {
        String suri = "/".concat(sURL.replaceFirst(szYTHOSTREGEX, ""));
        return(suri);
    }

    public static String getHost(String sURL) {
        String shost = sURL.replaceFirst(szYTHOSTREGEX, "");
        shost = sURL.substring(0, sURL.length()-shost.length());
        shost = shost.toLowerCase().replaceFirst("http://", "").replaceAll("/", "");
        return(shost);
    }
}
