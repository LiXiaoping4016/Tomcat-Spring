package com.Entity;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Http返回包
 *
 * @Author lixp23692
 * @Date 2020.03.31
 */
public class HttpRespone {
    private OutputStream outputStream;

    public HttpRespone(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String content) throws IOException {
        StringBuffer httpResponse = new StringBuffer();
        httpResponse.append("HTTP/1.1 200 OK\n")
                .append("Content-Type:text/html\n")
                .append("\r\n")
                .append("<html><head><link rel=\"icon\" href=\"data:;base64,=\"></head><body>")
                .append(content)
                .append("</body></html>");
        outputStream.write(httpResponse.toString().getBytes());
        outputStream.close();
    }
}
