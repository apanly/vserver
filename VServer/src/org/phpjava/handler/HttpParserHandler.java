/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.handler;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.phpjava.config.Language;
import org.phpjava.core.HttpHeadAnalyse;
import org.phpjava.core.Request;
import org.phpjava.core.Response;
import org.phpjava.event.EventAdapter;
import org.phpjava.reqtype.TypeManager;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class HttpParserHandler extends EventAdapter {

    public final static String EOL = "\r\n";

    public HttpParserHandler() {
    }

    @Override
    public void onBeforeRead(Request request) throws Exception {
        //super.onBeforeRead(client, request);
        LogHelper.info(request.getSc().socket().getRemoteSocketAddress() + " " + Language.socketLinked);
    }


    @Override
    public void onReading(Request request) throws Exception {
        (new HttpHeadAnalyse(request)).run();
        LogHelper.info(Language.reading + "(" + request.getSc().socket().getRemoteSocketAddress() + ")" + request.getPath());
        (new TypeManager(request)).run();
        onReiveFile(request);
    }

    @Override
    public void onWriting(Request request, Response response) throws Exception {
        LogHelper.info(Language.writing + "(" + request.getSc().socket().getRemoteSocketAddress() + ")"+"  "+Language.sendFile+request.getPath());
        DateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        StringBuffer sb = new StringBuffer();
        sb.append(request.getHeaderByKey("Server_Protocol") + request.getStatus() + EOL);
        sb.append("Date: " + date.format(new Date()) + EOL);
        Object[] cgiResHead = request.getCgiResponseHead();
        if (cgiResHead != null && cgiResHead.length > 0) {
            for (Object tmp : cgiResHead) {
                sb.append(tmp.toString() + EOL);
            }
        }
        sb.append("Content-Length: " + request.getCgiResponseBody().length + EOL);
        sb.append("Content-Type: " + request.getPageType() + EOL + EOL);
        response.setResponseHeader(sb.toString());
        response.send(request.getCgiResponseBody());
    }

    @Override
    public void onClose(Request request) throws IOException {
        LogHelper.info(Language.close + "(" + request.getSc().socket().getRemoteSocketAddress() + ")");
        SocketChannel sc = (SocketChannel) request.getSc();
        sc.finishConnect();
        sc.close();
        sc.socket().close();
    }
    
    @Override
    public void onReiveFile(Request request) throws Exception {
        String data = (String) request.getMessBody();
        if (data != null) {
            String filename=get(data,"filename");
            String tmpkey=get(data,"Content-Type");
            tmpkey="Content-Type: "+tmpkey;
            int start=data.indexOf(tmpkey);
            String boundary=request.getHeaderByKey("Content-Type");
            boundary=boundary.substring(boundary.indexOf("boundary=")+9);
            int end=data.lastIndexOf(boundary);
            String filecontent = data.substring(start+tmpkey.length()+2, end);
            if(filecontent.length()>0){
              LogHelper.writeFile("/home/vincent/vserver/down/"+filename, filecontent);   
            }

        }
    }
    
    private String get(String data,String s){
         int start = data.indexOf(s);
        int end = data.indexOf(10, start);
        if ((start >= 0) && (end <= data.length()) && (end > 0)) {
            if(data.charAt(start + s.length()) == ':'){
               return data.substring(start + s.length() + 1, end).trim(); 
            }
            if(data.charAt(start + s.length()) == '='){
                 return data.substring(start + s.length() + 2, end-2).trim(); 
            }
            
        }
        return null;
    }
}
