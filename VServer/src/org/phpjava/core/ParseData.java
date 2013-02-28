/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.util.HashMap;
import java.util.StringTokenizer;
import org.phpjava.config.Config;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class ParseData {

//    private Request request = null;
//    public final static int GET = 1, POST = 1 << 1, PUT = 1 << 2, DELETE = 1 << 3, HEAD = 1 << 4, INPUT = 1 << 5;
//    private boolean flag;
//    public final static String EOL = "\r\n";
//
//    public ParseData(Request request) {
//        this.request = request;
//    }
//    public void run(){
//        String root = Config.rootPath;
//        String postData = EOL;
//        request.setRoot(root);
//        String data = new String(request.getDataInput());
//        String[] httpData = data.split(EOL);
//        int tmplength = httpData.length;
//        for (int i = 0; i < tmplength; i++) {
//            if (i == 0) {
//                DealFirstLine(httpData[i], request);
//            } else {
//                if (!flag && httpData[i].trim().isEmpty()) {
//                    flag = true;
//                    continue;
//                }
//                if (flag) {
//                    postData += httpData[i] + EOL;
//                    continue;
//                }
//                DealLines(httpData[i], request);
//            }
//        }
//        if (flag) {
//            DealMultiParameters(postData, request);
//        }
//        flag = false;
//        String path = request.getRequestPath();
//        if ("/".equals(path)) {
//            path = root + "/"+Config.defaultIndex;
//            request.setPageType("text/html; charset=UTF-8");
//        } else {
//            path = root + path;
//            if (path.endsWith("css")) {
//                request.setPageType("text/css");
//            } else if (path.endsWith("js")) {
//                request.setPageType("application/javascript");
//            } else if (path.endsWith("png")) {
//                request.setPageType("image/png");
//            } else {
//                request.setPageType("text/html; charset=UTF-8");
//            }
//        }
//        request.setPath(path);
//    }
//    private void DealFirstLine(String line, Request request) {
//        String path = "", version;
//        String parameters;
//        int method = 1;
//        if (line.isEmpty()) {
//            return;
//        }
//        StringTokenizer st = new StringTokenizer(line);
//        String data = st.nextToken();
//        if (data.equalsIgnoreCase("get")) {
//            method = GET;
//        } else if (data.equalsIgnoreCase("post")) {
//            method = POST;
//        } else if (data.equalsIgnoreCase("put")) {
//            method = PUT;
//        } else if (data.equalsIgnoreCase("delete")) {
//            method = DELETE;
//        } else if (data.equalsIgnoreCase("head")) {
//            method = HEAD;
//        } else if (data.equalsIgnoreCase("input")) {
//            method = INPUT;
//        }
//        request.setMethod(method);
//        data = st.nextToken();
//        int index = data.indexOf("?");
//        if (index > -1) {
//            path = data.substring(0, index);
//            parameters = data.substring(index + 1);
//        } else {
//            path = data;
//            parameters = null;
//        }
//        DealWwwParameters(parameters, request, "get");
//        version = st.nextToken();
//        request.setVersion(version);
//        request.setRequestPath(path);
//    }
//
//    private void DealLines(String line, Request request) {
//        int index = line.indexOf(":");
//        String name, value;
//        if (index > -1) {
//            name = line.substring(0, index).toLowerCase();
//            value = line.substring(index + 1).trim();
//            request.addHeader(name, value);
//        }
//    }
//
//    private void DealWwwParameters(String para, Request request, String method) {
//        if (para != null && !para.isEmpty()) {
//            String[] data = para.split("&");
//            for (String keyval : data) {
//                int index = keyval.indexOf("=");
//                if (index <= 0) {
//                    continue;
//                }
//                if (method.equalsIgnoreCase("get")) {
//                    request.addWwwGetData(keyval.substring(0, index - 1), keyval.substring(index + 1));
//                } else {
//                    request.addWwwPostData(keyval.substring(0, index - 1), keyval.substring(index + 1));
//                }
//            }
//        }
//    }
//
//    private void DealMultiParameters(String data, Request request) {
//        String type = request.getHeaderByKey("content-type");
//        if (type != null && !type.isEmpty()) {
//            int index1 = type.indexOf("multipart/form-data");
//            int index2 = type.indexOf("application/x-www-form-urlencoded");
//            if (index1 == -1 && index2 == -1) {
//                return;
//            }
//            if (index1 >= 0) {
//                String boundary = type.substring(type.indexOf("boundary=") + 9);
//                String[] postData = data.split(EOL + "--" + boundary + EOL);
//                int length = postData.length;
//                for (int i = 0; i < length; i++) {
//                    if (i == 0) {
//                        continue;
//                    }
//                    parseMultiParam(postData[i], request, boundary);
//                }
//            }
//            if (index2 >= 0) {
//                DealWwwParameters(data, request, "post");
//            }
//        }
//    }
//
//    private void parseMultiParam(String data, Request request, String boundary) {
//        int pos = data.indexOf("filename=");
//        String s;
//        String[] tmp;
//        int index;
//        if (pos >= 0) {
//            index = data.indexOf("name=") + "name=".length() + 1;
//            HashMap<String, String> file = new HashMap();
//            s = data.substring(index);
//            index = s.indexOf(";");
//            file.put("name", s.substring(0, index - 1));
//            pos += "filename=".length() + 1;
//            s = data.substring(pos);
//            index = s.indexOf("\"");
//            file.put("filename", s.substring(0, index));
//            index = data.indexOf("Content-Type:") + "Content-Type:".length() + 1;
//            s = data.substring(index);
//            pos = s.indexOf(EOL) + EOL.length();
//            file.put("type", s.substring(0, pos).trim());
//            file.put("data", s.substring(pos).replace("--" + boundary + "--", ""));
//            request.addMultipartPostData("file", "filedata");
//            request.addMultipartPostData("filedata", file);
//            try {
//                Notifiter.getInstance().fireOnReceiveFile(request);
//            } catch (Exception e) {
//                LogHelper.log("a error occurs where fireReceiveFile:" + e.getMessage());
//                e.printStackTrace();
//            }
//        } else {
//            index = data.indexOf("name=") + "name=".length() + 1;
//            s = data.substring(index);
//            tmp = s.split(EOL);
//            if (tmp.length < 2) {
//                request.addMultipartPostData(tmp[0].substring(0, tmp[0].length() - 1).trim(), "");
//            } else {
//                request.addMultipartPostData(tmp[0].substring(0, tmp[0].length() - 1).trim(), tmp[2].trim());
//            }
//        }
//        pos = -1;
//        s = null;
//        tmp = null;
//        index = -1;
//    }
}
