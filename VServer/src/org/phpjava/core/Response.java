/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class Response {

    private SocketChannel sc;
    private String responseHeader = "";
    public Response(SocketChannel sc) {
        this.sc = sc;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader += responseHeader;
    }

    /**
     * 向客户端写数据
     * @param data byte[]　待回应数据
     */
    public void send(byte[] data) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(responseHeader.getBytes().length+data.length);
        buffer.put(responseHeader.getBytes(),0,responseHeader.getBytes().length);
        buffer.put(data, 0, data.length);
        buffer.flip();
        sc.write(buffer);
    }
}
