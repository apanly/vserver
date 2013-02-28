/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.phpjava.reqtype;

import org.phpjava.core.Request;

/**
 *
 * @author vincent
 */
public class TypeManager {

    private String path = null;
    private Request request;

    public TypeManager(Request request) {
        this.request = request;
        this.path = request.getPath();
    }

    public void run() {
        if (path.endsWith(".php")) {
            try {
                (new CGIPHP()).run(request);
            } catch (Exception e) {
            }
        } else {
            (new TypeBase()).reqRead(request);
        }
    }
}
