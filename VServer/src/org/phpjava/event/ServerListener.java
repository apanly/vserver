/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.event;

import java.nio.channels.SocketChannel;
import org.phpjava.core.Request;
import org.phpjava.core.Response;

/**
 *
 * @author vincent
 */
public interface ServerListener {
    public void onError(String error);
    public void onBeforeAccept() throws Exception;
    public void onAccepting() throws Exception;
    public void onAccepted() throws Exception;
    public void onBeforeRead(Request request) throws Exception;
    public void onReading(Request request) throws Exception;
    public void onReaded(Request request) throws Exception;
    public void onReiveFile(Request request) throws Exception ;
    public void onBeforeWrite(Request request,Response response) throws Exception;
    public void onWriting(Request request,Response response) throws Exception;
    public void onWrited(Request request,Response response) throws Exception;
    public void onClose(Request request) throws Exception;
}
