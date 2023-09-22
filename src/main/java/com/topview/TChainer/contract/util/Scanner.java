package com.topview.TChainer.contract.util;

import com.topview.TChainer.contract.StorageTemplate;
import com.topview.TChainer.contract.util.ContractGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 扫描util
 * @author 刘家辉
 * @date 2023/09/22
 */
public class Scanner {

    public static List<Class<?>> scan(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static List<Class<?>> findAnnotations(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = scan(packageName);
        List<Class<?>> templates = new ArrayList<>();
        for (Class<?> clazz : classes) {
            Annotation[] classAnnotations = clazz.getAnnotations();
            for (Annotation annotation : classAnnotations) {
                if( annotation instanceof StorageTemplate){
                    templates.add(clazz);
                }
            }
        }
        return templates;
    }

}