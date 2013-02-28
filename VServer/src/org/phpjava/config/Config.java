/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.config;

/**
 *
 * @author vincent
 */
public class Config {
    public static String rootPath="/home/vincent/vserver";
    public static String errorLog="/home/vincent/vserver/down/log";
    public static String defaultIndex="index.html";
    public static String softName="VServer V0.1";
    
    public static int beforeAccept=10;
    public static int accepting=9;
    public static int accepted=8;
    public static int beforeRead=7;
    public static int reading=6;
    public static int readed=5;
    public static int beforeWrite=4;
    public static int writing=3;
    public static int writed=2;
    public static final int close=-10;
    
    public static int eventFlag=10;
   
}

