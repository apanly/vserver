/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.File;
import java.net.URLDecoder;
import org.phpjava.config.Config;
import org.phpjava.config.Language;
import org.phpjava.exception.BadRangeException;
import org.phpjava.util.LogHelper;

public class HttpHeadAnalyse {

    public static final String[] HttpField = {
        "Host", "User-Agent", "Accept", "Referer", "Accept-Language",
        "Content-Type", "Content-Length", "Cache-Control",
        "Accept-Encoding", "UA-CPU"};
    public static final char cr = 13;//\r
    public static final char lf = 10;//\n
    public final String GET = "GET";
    public final String HEAD = "HEAD";
    public final String OPTIONS = "OPTIONS";
    public final String POST = "POST";
    public final String PUT = "PUT";
    public final String DELETE = "DELETE";
    public final String TRACE = "TRACE";
    public final String CONNECT = "CONNECT";
    private String httphead;
    private String messBody;
    private Request request = null;
    public final static String EOL = "\r\n";

    public HttpHeadAnalyse(Request request) {
        this.request = request;
    }

    public void run() {
        request.setRoot(Config.rootPath);
        boolean flag = false;
        String data = new String(request.getDataInput());
        String[] httpData = data.split(EOL);
        StringBuffer sb = new StringBuffer();
        StringBuffer messBody = new StringBuffer();
        for (String tmp : httpData) {
            if (!flag && tmp.trim().isEmpty()) {
                flag = true;
                continue;
            }
            if (flag) {
                messBody.append(tmp).append(EOL);
            } else {
                sb.append(tmp).append(EOL);
            }
        }
        this.httphead = sb.toString();
        this.messBody = messBody.toString();
        sb = null;
        messBody = null;   
        request.setPath(getRequestFile().getAbsolutePath());                                                  
        init();
    }

    private void init() {

        request.setHttphead(httphead);

        request.setMessBody(messBody);
        
        addHead("Server_Protocol",getHttpVersion());

        addHead("Content-Type", get("Content-Type"));

        addHead("Query_String", getArguments());

        addHead("Server_Name", getHost());


        addHead("Accept-Encoding", get("Accept-Encoding"));

        addHead("Accept-Language", get("Accept-Language"));

        addHead("Referer", getRef());

        addHead("User-Agent", get("User-Agent"));

        addHead("Request_Method",getMethod());
        
        String accept=get("Accept");
        
        addHead("Accept", accept);
        
        String[] acceptinfo=accept.split(",");
        
        request.setPageType(acceptinfo[0]);
        
        accept=null;
        acceptinfo=null;
            
    }

    private void addHead(String key, String val) {
        request.addHeader(key, val);
    }

    public String get(String s) {
        int start = this.httphead.indexOf(s);
        int end = this.httphead.indexOf(10, start);
        if ((start >= 0) && (end <= this.httphead.length()) && (end > 0) && (this.httphead.charAt(start + s.length()) == ':')) {
            return this.httphead.substring(start + s.length() + 1, end).trim();
        }
        return null;
    }

    public String getHost() {
        String host = get("Host");
        int portbegin = host.indexOf(58);
        if (portbegin > 0) {
            host = host.substring(0, portbegin);
        }
        return host;
    }

    public String getArguments() {
        String arg = getRequestURI();
        int begin = arg.indexOf(63);
        if ((begin < 0) || (begin >= arg.length() - 1)) {
            return null;
        }
        arg = arg.substring(begin + 1);
        return arg;
    }
    public String getHttpVersion(){
        int start = this.httphead.indexOf("HTTP");
        return this.httphead.substring(start, start+8);
    }
    public String getRequestURI() {
        String requesturi = null;
        int start = this.httphead.indexOf(32);
        int end = this.httphead.indexOf(32, start + 1);
        if ((start >= 0) && (end >= 0)) {
            requesturi = this.httphead.substring(start + 1, end);
            if (requesturi != null) {
                requesturi = decodeURI(requesturi);
            }
        }
        return requesturi;
    }

    public String getRef() {
        String referer = get("Referer");
        if (referer != null) {
            referer = decodeURI(referer);
            int index = referer.indexOf("http://");
            if (index >= 0) {
                index = referer.indexOf(47, index + "http://".length());
                if (index >= 0) {
                    referer = referer.substring(index);
                }
            }
        }
        return referer;
    }

    public File getRequestFile() {
        File requestfile = null;
        String name = getRequestURI();
        int sp = name.indexOf("?");
        if (sp >= 0) {
            name = name.substring(0, sp);
        }

        File file = new File(request.getRoot() + File.separator+name);
        //File reff = new File(request.getRoot() + getRef());这个是获取refer的

        if (file.isDirectory()) {
            file = conversIndexfile(file);
        }
        if(file.isFile()){
           requestfile = file;
        }else{
           request.setStatus(404);
           requestfile = new File(request.getRoot() + File.separator+"404.html");
        }
        return requestfile;
    }

    private final String decodeURI(String uri) {
        int fen = uri.indexOf(63);
        String url_s = null;
        String url_e = null;
        if ((fen >= 0) && (fen < uri.length() - 1)) {
            url_s = uri.substring(0, fen);
            url_e = uri.substring(fen + 1);
        } else {
            url_s = uri;
            url_e = null;
        }
        try {
            url_s = URLDecoder.decode(url_s, "UTF-8");
            uri = url_s + ((url_e == null) ? "" : new StringBuilder("?").append(url_e).toString());
        } catch (Exception e) {
            LogHelper.error(Language.URLDecoderException + ":" + uri);
        }
        return uri;
    }

    private File conversIndexfile(File f) {
        String[] defaultIndex = {"index.html", "index.php", "index.htm"};
        int circle = defaultIndex.length;
        String indexfile = f.getPath();
        File newfile = null;
        for (int i = 0; i < circle; ++i) {
            newfile = new File(indexfile + File.separatorChar + defaultIndex[i]);
            if (newfile.isFile()) {
                return newfile;
            }
        }
        return f;
    }

    public Range getRange() throws BadRangeException {
        String range = get("Range");
        if (range != null) {
            return new Range(range);
        }
        return null;
    }

    public boolean isGET() {
        return (this.httphead.startsWith("GET"));
    }

    public boolean isPOST() {
        return (this.httphead.startsWith("POST"));
    }

    public String getMethod() {
        int end = this.httphead.indexOf(32);
        return this.httphead.substring(0, end);
    }
}