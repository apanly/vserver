/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.phpjava.config.Language;
import org.phpjava.config.Status;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class Server implements Runnable {

    private int port = 9090;
    private int timeout = 10;
    private static Selector selector;
    private static Notifiter notifiter;
    private static List<SelectionKey> pool = new LinkedList();
    private static int MAX_THREADS = 3;

    public Server(int port) throws Exception {
        if (port <= 1024 || port > 65535) {
            LogHelper.log("端口不符合要求，请填写介于1024～65535之间的端口");
            LogHelper.exit(0);
        } else {
            this.port = port;
        }
        notifiter = Notifiter.getInstance();
        // 创建读写线程池
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread r = new Reader();
            Thread w = new Writer();
            r.start();
            w.start();
        }
        /**********init operation*******************/
           Status.init();
        /*******************************************/
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerSocket ss = server.socket();
        ss.setSoTimeout(timeout);
        ss.bind(new InetSocketAddress(port));
        selector = Selector.open();
        selector.open();
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

    }

    @Override
    public void run() {
        LogHelper.debug("server started ............");
        LogHelper.debug("listening on " + port);
        int num = 0;
        SelectionKey key;
        while (true) {
            try {
                num = selector.select();
                if (num > 0) {
                    Set selectorkeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectorkeys.iterator();
                    while (it.hasNext()) {
                        key = it.next();
                        if (!key.isValid()) {
                            continue;
                        }
                        it.remove();
                        if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                            //fire accept and accepter
                            notifiter.fireOnBeforeAccept();
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            notifiter.fireOnAccepting();
                            notifiter.fireOnAccepted();
                            client.socket().setSoTimeout(timeout);
                            Request request = new Request(client);
                            client.configureBlocking(false);
                            LogHelper.debug(Language.registerReading+":"+client.socket().getRemoteSocketAddress());
                            client.register(selector, SelectionKey.OP_READ, request);
                        } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                            Reader.processRequest(key);
                            key.cancel();
                        } else if ((key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
                            Writer.processRequest(key);
                            key.cancel();
                        }
                    }
                } else {
                    //LogHelper.log("register");
                    addRegister();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void addRegister() {
        synchronized (pool) {
            while (!pool.isEmpty()) {
                SelectionKey key = pool.remove(0);
                SocketChannel client = (SocketChannel) key.channel();
                try {
                    LogHelper.debug(Language.registerWriting+":"+client.socket().getRemoteSocketAddress());
                    client.register(selector, SelectionKey.OP_WRITE, key.attachment());
                } catch (Exception e) {
                    try {
                        client.finishConnect();
                        client.close();
                        client.socket().close();
                        notifiter.fireOnClose((Request) key.attachment());
                    } catch (Exception e1) {
                        LogHelper.error("an error occurs where register:" + e.getMessage());
                    }
                }
            }
        }
    }
    public static int cnt = 1;

    public static void addWriteKey(SelectionKey key) {
        synchronized (pool) {
            if (!pool.contains(key)) {
                pool.add(pool.size(), key);
                pool.notifyAll();
//                Request request = (Request) key.attachment();
//                LogHelper.log("request:" + request.getRequestPath());
            }
        }
        selector.wakeup();
    }
}
