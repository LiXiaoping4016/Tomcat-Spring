package com.servelet;

import com.Entity.HttpRequest;
import com.Entity.HttpRespone;

/**
 * Servelet抽象类
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
public abstract class HttpServelet {

    public void service(HttpRequest request, HttpRespone response) {
        String requestType = request.getRequestType();
        if ("POST".equalsIgnoreCase(requestType)) {
            doPost(request, response);
        } else if ("GET".equalsIgnoreCase(requestType)) {
            doGet(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpRespone response);

    public abstract void doPost(HttpRequest request, HttpRespone response);
}
