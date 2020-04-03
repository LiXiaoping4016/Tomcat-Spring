package com.Entity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Http请求包
 *
 * @Author lixp23692
 * @Date 2020.03.31
 */
public class HttpRequest {
    private String url;
    /**
     * 请求类型
     */
    private String requestType;

    // 读取输入字节流，封装成字符串格式的请求内容
    public HttpRequest(InputStream inputStream) throws IOException {
        String httpRequest = "";

        byte[] httpRequestBytes = new byte[1024];

        int length = 0;

        if ((length = inputStream.read(httpRequestBytes)) > 0) {
            httpRequest = new String(httpRequestBytes, 0, length);
        }
        // HTTP请求协议:首行的内容依次为：请求方法、请求路径以及请求协议及其对应版本号
        //   GET    /index  HTTP/1.1
        if (httpRequest == null || httpRequest == "") {
            System.out.println("######当前请求为空");
            return;
        }
        String httpHead = httpRequest.split("\r\n")[0];
        requestType = httpHead.split("\\s")[0];
        url = httpHead.split("\\s")[1];
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "HttpRequest [url=" + url + ", method=" + requestType + "]";
    }
}
