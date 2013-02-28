/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.test;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author vincent
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            File file = new File("/home/vincent/vserver/down/common.png");
            FileInputStream is = new FileInputStream(file);
            //BufferedReader br=new BufferedReader(new FileReader(file));
            //String tmp=new String(br.readLine().getBytes(),"utf8");
            //System.out.println(tmp);
            //br.close();

            File file2 = new File("/home/vincent/vserver/down/common.png");
            FileOutputStream os = new FileOutputStream(file2);
            BufferedWriter bw=new BufferedWriter(new FileWriter(file2));
           // bw.write(tmp);
            //bw.close();

            byte[] buf = new byte[1024];
            int len = 0;
            while((len=is.read(buf))!=-1){
                os.write(buf, 0, len);
                System.out.println(new String(buf));
            }

        } catch (Exception e) {
        }
    }
}
