package com;

import com.Entity.HttpRequest;
import com.Entity.HttpRespone;
import com.annotation.Autowired;
import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.annotation.Service;
import com.servelet.HttpServelet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DispatcherServlet
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
public class DispatcherServlet extends HttpServelet {
    static String path = "E:\\SpringCloud\\Tomcat-Spring\\Spring\\src\\main\\java\\com";
    static String rootPath = "E:\\SpringCloud\\Tomcat-Spring\\Spring\\src\\main\\java\\";
    static String suffix = ".java";
    static String glob = "glob:**/*" + suffix;
    // beanName -> bean
    static Map<String, Object> beanContain = new HashMap<>();
    // url -> beanName
    static Map<String, String> urlController = new HashMap<>();
    // url -> method
    static Map<String, Method> urlMethod = new HashMap<>();

    public static void main(String[] args) {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.initContain();

        String url = "/order/giveOrder";
        System.out.println("我收到一个请求：" + url);
        if (!urlMethod.containsKey(url)) {
            System.out.println("404......" + url + " not found......");
            return;
        }

        Object bean = beanContain.get(urlController.get(url));
        Method method = urlMethod.get(url);
        Object[] parameters = Arrays.stream(method.getParameters()).map(parameter -> {
            Class<?> type = parameter.getType();
            return null;
        }).collect(Collectors.toList()).toArray();

        try {
            Object result = method.invoke(bean, parameters);
            System.out.println((String) result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initContain() {
        try {
            List<String> classFullName = doScanner(glob, path);
            doInstance(classFullName);
            doAutowired();
            initHandleMapping();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        beanContain.entrySet().stream()
                .forEach(entry -> {
                    Arrays.stream(entry.getValue().getClass().getDeclaredFields())
                            .filter(field -> field.isAnnotationPresent(Autowired.class))
                            .forEach(field -> {
                                field.setAccessible(true);
                                String[] split = field.getGenericType().getTypeName().split("\\.");
                                String beanName = toLowerFirstWord(split[split.length - 1]);
                                try {
                                    field.set(entry.getValue(), beanContain.get(beanName));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            });

                });
    }

    private void initHandleMapping() {
        beanContain.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().isAnnotationPresent(Controller.class))
                .forEach(entry -> {
                    Class controllerClass = entry.getValue().getClass();
                    StringBuilder baseUrl = new StringBuilder();
                    if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                        baseUrl.append(((RequestMapping) controllerClass.getAnnotation(RequestMapping.class)).value());
                    }
                    final String controller = baseUrl.toString();
                    Arrays.stream(controllerClass.getMethods())
                            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                            .forEach(method -> {
                                String requestUrl = method.getAnnotation(RequestMapping.class).value();
                                urlController.put(controller + requestUrl, entry.getKey());
                                urlMethod.put(controller + requestUrl, method);
                            });
                });
    }

    private void doInstance(List<String> classFullNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String classFullName : classFullNames) {
            Class<?> aClass = Class.forName(classFullName);
            if (aClass.isAnnotationPresent(Controller.class) || aClass.isAnnotationPresent(Service.class)) {
                beanContain.put(toLowerFirstWord(aClass.getSimpleName()), aClass.newInstance());
            }
        }
    }

    private String toLowerFirstWord(String oldValue) {
        char[] chars = oldValue.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private List<String> doScanner(String glob, String path) throws IOException {
        List<String> files = new ArrayList<>();

        final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                if (pathMatcher.matches(path)) {
                    files.add(path.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<String> clazz = files.stream().map(e -> e.replace(rootPath, "")
                .replace("\\", ".")
                .replace(suffix, "")).collect(Collectors.toList());
        return clazz;
    }


    public void doGet(HttpRequest request, HttpRespone response) {
        String url = request.getUrl();
        System.out.println("我收到一个请求：" + url);
        if (!urlMethod.containsKey(url)) {
            try {
                response.write("404......" + url + " not found......");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Object bean = beanContain.get(urlController.get(url));
        Method method = urlMethod.get(url);

        Object[] parameters = Arrays.stream(method.getParameters()).map(parameter -> {
            Class<?> type = parameter.getType();
            if (HttpRequest.class.isAssignableFrom(type)) {
                return request;
            } else if (HttpRespone.class.isAssignableFrom(type)) {
                return response;
            }
            return null;
        }).collect(Collectors.toList()).toArray();

        try {
            Object result = method.invoke(bean, parameters);
            response.write((String) result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpRequest request, HttpRespone response) {

    }
}
