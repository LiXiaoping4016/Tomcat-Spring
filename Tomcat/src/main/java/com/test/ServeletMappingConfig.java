package com.test;

import com.servelet.ServeletMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * ServeletMappingConfig
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
public class ServeletMappingConfig {
    public static List<ServeletMapping> serveletMappingList = new ArrayList<ServeletMapping>();

    static {
        serveletMappingList.add(new ServeletMapping("test", "/index", "com.test.IndexServlet"));
        serveletMappingList.add(new ServeletMapping("test", "/myblog", "com.test.MyBlog"));
    }
}
