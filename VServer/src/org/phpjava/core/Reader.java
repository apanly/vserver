/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class Reader extends Thread {

    private static List<SelectionKey> pool = new LinkedList();
    private static Notifiter notifiter = Notifiter.getInstance();

    public void run() {
        while (true) {
            try {
                SelectionKey key;
                synchronized (pool) {
                    while (pool.isEmpty()) {
                        pool.wait();
                    }
                    key = pool.remove(0);
                }
                read(key);
            } catch (InterruptedException ie) {
                break;
            }
        }
    }

    private void read(SelectionKey key) {
         Request request = (Request) key.attachment();
        try {
            SocketChannel client = (SocketChannel) key.channel();
           
            notifiter.fireOnBeforeRead(request);
            byte[] data = readProcess(client);
            request.setDataInput(data);
            /*if (data.length <= 10) {
                notifiter.fireOnClose(request);
                return;
            }*/
            notifiter.fireOnReading(request);
            notifiter.fireOnReaded(request);
            Server.addWriteKey(key);
        } catch (Exception e) {
             try {
             request.setStatus(500);
            } catch (Exception closee) {
                
            }
            LogHelper.error("a error occurs where reading:" + e.getMessage());
            //e.printStackTrace();
        }
    }

    private static int BUFFER_SIZE = 1024;

    public byte[] readProcess(SocketChannel client) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        byte[] data = new byte[BUFFER_SIZE * 2];
        int r = 0;
        int off = 0;
        while (true) {
            buffer.clear();
            r = client.read(buffer);
            if (r == -1 || r == 0) {
                break;
            }
            if ((off + r) > data.length) {
                data = grow(data, BUFFER_SIZE * 10);
            }
            System.arraycopy(buffer.array(), 0, data, off, r);
            off += r;
        }
        byte[] req = new byte[off];
        System.arraycopy(data, 0, req, 0, off);
        return req;

    }

    /**
     * 数组扩容
     * @param src byte[] 源数组数据
     * @param size int 扩容的增加量
     * @return byte[] 扩容后的数组
     */
    public static byte[] grow(byte[] src, int size) {
        byte[] tmp = new byte[src.length + size];
        System.arraycopy(src, 0, tmp, 0, src.length);
        return tmp;
    }

    public static void processRequest(SelectionKey key) {
        synchronized (pool) {
            if (!pool.contains(key)) {
                pool.add(pool.size(), key);
                pool.notifyAll();
            }
        }
    }
}
