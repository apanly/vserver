/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.start;

import java.io.File;
import org.phpjava.handler.HttpParserHandler;
import org.phpjava.core.Notifiter;
import org.phpjava.core.Server;
import org.phpjava.util.LogHelper;

/**
 *
 * @author vincent
 */
public class BootStart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            HttpParserHandler httpParser = new HttpParserHandler();
            Notifiter notifiter = Notifiter.getInstance();
            notifiter.addListener(httpParser);
            Server server = new Server(10000);
            Thread t = new Thread(server);
            t.start();
        } catch (Exception e) {
            LogHelper.error(e.getMessage());
        }
    }
}
