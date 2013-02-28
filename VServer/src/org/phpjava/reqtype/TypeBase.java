/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.reqtype;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.phpjava.config.Config;
import org.phpjava.core.Request;

/**
 *
 * @author vincent
 */
public class TypeBase {

    public void reqRead(Request request) {
        String root = Config.rootPath;
        File file = new File(request.getPath());
        byte[] parserResult = new byte[0];
        try {
            if (file.exists() && file.canRead() && file.getCanonicalPath().startsWith(root)) {
                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                parserResult = new byte[(int) file.length()];
                fis.readFully(parserResult);
                fis.close();
            }
        } catch (IOException e) {
            
        }
        request.setCgiResponseBody(parserResult);
    }
}
