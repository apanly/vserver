/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.core;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import org.phpjava.config.Config;
import org.phpjava.config.Language;
import org.phpjava.event.ServerListener;
import org.phpjava.util.LogHelper;
import org.phpjava.util.VariUtil;

/**
 * beforeAccept                 10
 * accepting                    9
 * accepted                     8
 * 
 * beforeRead                   7
 * reading                      6
 * readed                       5
 * 
 * beforeWrite                  4
 * writing                      3
 * writed                       2
 * 
 * close                        11
 * 
 * @author vincent
 */
public class Notifiter {

    private static List<ServerListener> listeners = null;
    private static Notifiter instance = null;
    private int size = 0;

    public Notifiter() {
        listeners = new ArrayList();
    }

    public static synchronized Notifiter getInstance() {
        if (instance == null) {
            instance = new Notifiter();
        }
        return instance;
    }

    public void addListener(ServerListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
                size++;
            }
        }
    }

    public void fireOnBeforeAccept() throws Exception {
        if (Config.eventFlag >= Config.beforeAccept) {
            LogHelper.debug(Language.doBeforeAccept);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onBeforeAccept();
            }
        } else {
            LogHelper.debug(Language.skipBeforeAccept);
        }
    }

    public void fireOnAccepting() throws Exception {
        if (Config.eventFlag >= Config.accepting) {
             LogHelper.debug(Language.doAccepting);
             VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onAccepting();
            }
        } else {
            LogHelper.debug(Language.skipAccepting);
        }
    }

    public void fireOnAccepted() throws Exception {
        LogHelper.debug(Language.doAccepted);
        VariUtil.calEventFlag();
        if (Config.eventFlag >= Config.accepted) {
            for (int i = 0; i < size; i++) {
                listeners.get(i).onAccepted();
            }
        } else {
            LogHelper.debug(Language.skipAccepted);
        }
    }

    public void fireOnBeforeRead(Request request) throws Exception {
        if (Config.eventFlag >= Config.beforeRead) {
            LogHelper.debug(Language.doBeforeRead);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onBeforeRead(request);
            }
        } else {
            LogHelper.debug(Language.skipBeforeRead);
        }
    }

    public void fireOnReading(Request request) throws Exception {
        if (Config.eventFlag >= Config.reading) {
            LogHelper.debug(Language.doReading);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onReading(request);
            }
        } else {
            LogHelper.debug(Language.skipReading);
        }
    }

    public void fireOnReaded(Request request) throws Exception {
        if (Config.eventFlag >= Config.readed) {
            LogHelper.debug(Language.doReaded);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onReaded(request);
            }
        } else {
            LogHelper.debug(Language.skipReaded);
        }
    }

    public void fireOnBeforeWrite(Request request, Response response) throws Exception {
        if (Config.eventFlag >= Config.beforeWrite) {
             LogHelper.debug(Language.doBeforeWrite);
             VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onBeforeWrite(request, response);
            }
        } else {
            LogHelper.debug(Language.skipBeforeWrite);
        }
    }

    public void fireOnWriting(Request request, Response response) throws Exception {
        if (Config.eventFlag >= Config.writing) {
            LogHelper.debug(Language.doWriting);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onWriting(request, response);
            }
        } else {
            LogHelper.debug(Language.skipWriting);
        }
    }

    public void fireOnWrited(Request request, Response response) throws Exception {
        if (Config.eventFlag >= Config.writing) {
            LogHelper.debug(Language.doWrited);
            VariUtil.calEventFlag();
            for (int i = 0; i < size; i++) {
                listeners.get(i).onWrited(request, response);
            }
        } else {
            LogHelper.debug(Language.skipWrited);
        }
    }

    public void fireOnClose(Request request) throws Exception {
        if (Config.eventFlag >= Config.close) {
            LogHelper.debug(Language.doClose);
            for (int i = 0; i < size; i++) {
                listeners.get(i).onClose(request);
            }
        } else {
            LogHelper.debug(Language.skipClose);
        }
    }

    public void fireOnReceiveFile(Request request) throws Exception {
        for (int i = 0; i < size; i++) {
            listeners.get(i).onReiveFile(request);
        }
    }

    public void fireOnError(String error) {
        for (int i = 0; i < size; i++) {
            listeners.get(i).onError(error);
        }
    }
    
    
}
