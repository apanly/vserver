/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.reqtype;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import org.phpjava.config.Config;
import org.phpjava.config.Language;
import org.phpjava.core.CharBuffer;
import org.phpjava.core.Request;
import org.phpjava.exception.CgiTimeOutException;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class CGIPHP {

    public void run(Request request) throws Exception {
        String[] env = getENV(request);
        String cgiPath = "/usr/bin/php5-cgi";
        Process cgi = Runtime.getRuntime().exec(cgiPath, env, new File(cgiPath).getParentFile());
        writePostMessage(cgi.getOutputStream(), request);
        CGIPHP.readCgiOutput rco = new CGIPHP.readCgiOutput(cgi);
        rco.start();
        try {
            rco.join(15000);
        } catch (InterruptedException localInterruptedException) {
        }
        if (!(rco.complete)) {
            cgi.destroy();
            throw new CgiTimeOutException("CGI " + Language.socketTimeout);
        }
        Object[] heads = rco.heads;
        byte[] data = rco.data;
        request.setCgiResponseHead(heads);
        request.setCgiResponseBody(data);
    }

    private void writePostMessage(OutputStream out, Request request) throws IOException {
        out.write(request.getMessBody().getBytes());
        out.flush();
    }

    private String[] getENV(Request request) {
        String[] env = new String[22];
        String scrname = sf(new File(request.getPath()).getName());
        String abspath = sf(new File(request.getPath()).getAbsolutePath());

        env[0] = "CONTENT_TYPE=" + sf(request.getHeaderByKey("Content-Type"));
        env[1] = "PATH_TRANSLATED=" + abspath;
        env[6] = "SCRIPT_NAME=" + scrname;
        env[12] = "PATH_INFO=" + sf(abspath.substring(0, abspath.length() - scrname.length()));

        env[2] = "QUERY_STRING=" + sf(request.getHeaderByKey("Query_String"));
        env[3] = "REMOTE_ADDR=" + sf(request.getRemoteAddress().getHostAddress());
        env[4] = "REMOTE_HOST=" + sf(request.getRemoteAddress().getHostName());
        env[5] = "REQUEST_METHOD=" + sf(request.getHeaderByKey("Request_Method"));
        env[7] = "SERVER_NAME=" + sf(request.getHeaderByKey("Server_Name"));
        env[8] = "SERVER_PORT=9090";
        env[9] = "SERVER_SOFTWARE="+Config.softName;

        env[10] = "SERVER_PROTOCOL="+sf(request.getHeaderByKey("Server_Protocol"));
        env[11] = "GATEWAY_INTERFACE=CGIPHP/1.1";
        env[13] = "REMOTE_IDENT=";
        env[14] = "REMOTE_USER=";
        env[15] = "AUTH_TYPE=";
        env[16] = "CONTENT_LENGTH=" + ((request.getMessBody().getBytes().length > 0) ? Integer.valueOf(request.getMessBody().getBytes().length) : "");

        env[17] = "ACCEPT=" + sf(request.getHeaderByKey("Accept"));
        env[18] = "ACCEPT_ENCODING=" + sf(request.getHeaderByKey("Accept-Encoding"));
        env[19] = "ACCEPT_LANGUAGE=" + sf(request.getHeaderByKey("Accept-Language"));
        env[20] = "REFFERER=" + sf(request.getHeaderByKey("Referer"));
        env[21] = "USER_AGENT=" + sf(request.getHeaderByKey("User-Agent"));
        LogHelper.debug(abspath);
        return env;
    }

    private final String sf(String s) {
        return ((s != null) ? s : "");
    }

    private Object[] readHead(InputStream in) throws IOException {
        ArrayList headlist = new ArrayList();
        String r = readLine(in);
        while ((r != null) && (r.trim().length() > 0)) {
            headlist.add(r);
            r = readLine(in);
        }
        return headlist.toArray();
    }

    private byte[] readData(InputStream in) throws IOException {
        ByteArrayOutputStream array = new ByteArrayOutputStream(2000);
        byte[] buff = new byte[255];
        int readc = in.read(buff);
        while (readc > 0) {
            array.write(buff, 0, readc);
            readc = in.read(buff);
        }
        return array.toByteArray();
    }

    public static String readLine(InputStream in)
            throws IOException {
        CharBuffer buff = new CharBuffer(150);
        int read = in.read();
        while ((read >= 0) && (read != 13) && (read != 10)) {
            buff.append(read);
            read = in.read();
        }
        if (read == 13) {
            read = in.read();
            if (read == 10) {
                return buff.toString();
            }
        }
        return buff.toString();
    }

    private class readCgiOutput extends Thread {

        Process cgi = null;
        public Object[] heads = null;
        public byte[] data = null;
        public boolean complete;

        public readCgiOutput(Process paramProcess) {
            this.cgi = paramProcess;
            this.complete = false;
        }

        public void run() {
            this.complete = false;
            try {
                this.heads = CGIPHP.this.readHead(this.cgi.getInputStream());
                this.data = CGIPHP.this.readData(this.cgi.getInputStream());
                this.complete = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
