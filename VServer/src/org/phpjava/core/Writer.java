/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class Writer extends Thread {

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
                    key = (SelectionKey) pool.remove(0);
                }
                write(key);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public void write(SelectionKey key) {
        try {
           
            SocketChannel sc = (SocketChannel) key.channel();
            Response response = new Response(sc);
            Request request = (Request) key.attachment();
            notifiter.fireOnBeforeWrite(request, response);
            notifiter.fireOnWriting(request, response);
            notifiter.fireOnWrited(request, response);
            notifiter.fireOnClose(request);
        } catch (Exception e) {
            try {
                notifiter.fireOnClose((Request) key.attachment());
            } catch (Exception closee) {
            }
            LogHelper.error("an error occurs where write:" + e.getMessage());
        }
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
