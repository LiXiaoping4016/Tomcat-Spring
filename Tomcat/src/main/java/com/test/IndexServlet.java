package com.test;


import com.Entity.HttpRequest;
import com.Entity.HttpRespone;
import com.servelet.HttpServelet;

import java.io.IOException;

public class IndexServlet extends HttpServelet {
    @Override
    public void doGet(HttpRequest myRequest, HttpRespone response) {
        try {
            response.write("Hello, myTomcat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpRequest myRequest, HttpRespone response) {
        try {
            response.write("Hello, myTomcat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
