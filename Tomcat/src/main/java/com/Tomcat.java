package com;

import com.Entity.HttpRequest;
import com.Entity.HttpRespone;
import com.servelet.HttpServelet;
import com.servelet.ServeletMapping;
import com.test.ServeletMappingConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Tomcat启动类
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
public class Tomcat {
    private int port;
    private Map<String, String> urlServletMapping = new HashMap<String, String>();

    public Tomcat(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat(8080);
        tomcat.start();
    }

    public void start() {
        initServletMapping();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Tomcat is starting..., port:" + port);
            while (!serverSocket.isClosed()) {
                Socket connect = serverSocket.accept();
                InputStream inputStream = connect.getInputStream();
                OutputStream outputStream = connect.getOutputStream();

                HttpRequest request = new HttpRequest(inputStream);
                HttpRespone response = new HttpRespone(outputStream);
                String time = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
                System.out.println("[" + time + "]: 我收到一个请求：" + request.toString());
                dispatch(request, response);
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initServletMapping() {
        for (ServeletMapping servletMapping : ServeletMappingConfig.serveletMappingList) {
            urlServletMapping.put("/" + servletMapping.getServletName() + servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    public void dispatch(HttpRequest request, HttpRespone response) {
        String url = request.getUrl();
        if (url == null || url == "") {
            return;
        }
        String clazz = urlServletMapping.get(url);

        try {
            Class<HttpServelet> servletClass = (Class<HttpServelet>) Class.forName(clazz);
            HttpServelet servelet = servletClass.newInstance();
            servelet.service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.write("404....." + url + " not found...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
