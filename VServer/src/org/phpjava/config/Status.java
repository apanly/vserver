/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.config;

import java.util.HashMap;

/**
 *
 * @author vincent
 */
public class Status {
    private static HashMap<Integer, String> status;
    public static String getStatusByKey(int key) {
        return status.get(key);
    }
    public static void init(){
        status=new HashMap();
        status.put(200,"OK");
        status.put(404, "Not Found");
        status.put(500, "Internal Server Error");
    }
    
}
