/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import sun.misc.BASE64Decoder;
import org.phpjava.config.Config;

/**
 *
 * @author vincent
 */
public class LogHelper {

    public static void log(Object msg) {
        if (msg != null) {
            System.out.println(msg.toString());
        }
    }

    public static void exit(int flag) {
        System.exit(flag);
    }

    public static void fLog(String data) {
        try {
            writeFile(Config.errorLog, data);
        } catch (Exception e) {
        }
    }

    public static void writeFile(String path, String data) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            //BASE64Decoder base64=new BASE64Decoder();
            //fs.write(base64.decodeBuffer(data));
            fs.write(data.getBytes());
            fs.flush();
            fs.close();
            fs = null;
            file = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String  getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ly_time = sdf.format(new java.util.Date());
        return ly_time;
    }

    public static void error(Object message) {
        log("[Err] " +getDate()+" "+ message.toString());
    }

    public static void info(Object message) {
        log("[Info] " +getDate()+" "+ message.toString());
    }

    public static void debug(Object message) {
        log("[debug] " +getDate()+" "+ message.toString());
    }
    
}
