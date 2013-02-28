/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.start;

/**
 *
 * @author vincent
 */
public class laggage {
    //        try {
//            if (path.endsWith(".php")) {
//                String cgiPath = "php5-cgi";
//                Process cgi = Runtime.getRuntime().exec(cgiPath + " " + path);
//                BufferedReader br = new BufferedReader(new InputStreamReader(cgi.getInputStream()));
//                String line;
//                String tmp = "";
//                boolean flag = false;
//                while ((line = br.readLine()) != null) {
//                    if (line.length() == 0) {
//                        flag = true;
//                        continue;
//                    }
//                    if (flag) {
//                        tmp += line;
//                    } else {
//                        //这个地方需要处理，因为php一些特殊函数的数据会在前面告诉程序 例如header setCookie
//                    }
//                }
//                request.setHttpParser(tmp.getBytes());
//            } else {
//                File file = new File(path);
//                if (file.exists() && file.canRead() && file.getCanonicalPath().startsWith(root)) {
//                    DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
//                    byte[] parserResult = new byte[(int) file.length()];
//                    fis.readFully(parserResult);
//                    fis.close();
//                    request.setHttpParser(parserResult);
//                }
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    
    
    
    
//     String root = Config.rootPath;
//        String postData = "\r\n";
//        request.setRoot(root);
//        headers.clear();
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
//            request.setTmp("path:" + "defaultindex");
//            path = root + "/"+Config.defaultIndex;
//            request.setPageType("text/html; charset=UTF-8");
//        } else {
//            request.setTmp("path:" + path);
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
}
