/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.File;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import org.phpjava.config.Status;
/**
 *
 * @author vincent
 */
public class Request {

    private byte[] dataInput;
    private SocketChannel sc;
    private String root, pageType;
    private String path;
    private HashMap<String, String> header;
    private int status=200;
    public Request(SocketChannel sc) {
        this.sc = sc;
        header = new HashMap();
    }

    public SocketChannel getSc() {
        return sc;
    }

    public String getStatus() {
        return " "+String.valueOf(status)+" "+Status.getStatusByKey(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    public void close() {
        header = null;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public byte[] getDataInput() {
        return dataInput;
    }

    public void setDataInput(byte[] dataInput) {
        this.dataInput = dataInput;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /*****************new request****************************/
    private String httphead = null;
    private String messBody = null;
    private Object[] cgiResponseHead=null;
    private byte[] cgiResponseBody=null;
    public String getHttphead() {
        return httphead;
    }

    public void setHttphead(String httphead) {
        this.httphead = httphead;
    }

    public String getMessBody() {
        return this.messBody;
    }

    public void setMessBody(String messBody) {
        this.messBody = messBody;
    }


    public HashMap getHeader() {
        return header;
    }

    public String getHeaderByKey(String key) {
        return header.get(key);
    }

    public void addHeader(String key, String val) {
        if (!header.containsKey(key)) {
            header.put(key, val);
        }
    }

    
     public InetAddress getRemoteAddress() {
        return this.sc.socket().getInetAddress();
    }

    public byte[] getCgiResponseBody() {
        return cgiResponseBody;
    }

    public void setCgiResponseBody(byte[] cgiResponseBody) {
        this.cgiResponseBody = cgiResponseBody;
    }

    public Object[] getCgiResponseHead() {
        return cgiResponseHead;
    }

    public void setCgiResponseHead(Object[] cgiResponseHead) {
        this.cgiResponseHead = cgiResponseHead;
    }
    

}
