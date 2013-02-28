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
public  abstract class EventAdapter  implements ServerListener {
    public EventAdapter(){
        
    }

    @Override
    public void onReiveFile(Request request) throws Exception {
        
    }

    @Override
    public void onAccepting() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onBeforeAccept() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onBeforeRead(Request request) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onBeforeWrite(Request request, Response response) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onReaded(Request request) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onReading(Request request) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWrited(Request request, Response response) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWriting(Request request, Response response) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

    @Override
    public void onAccepted() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onError(String error) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

   

    @Override
    public void onClose(Request request) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
