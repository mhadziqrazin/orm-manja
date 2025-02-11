package org.vmj.parser;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassReader {
    public static List<Class<?>> readClasses(String compiledClassDirectory) throws Exception {
        File outDir = new File(compiledClassDirectory);
        if (!outDir.exists() || !outDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid compiled class directory: " + compiledClassDirectory);
        }

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{outDir.toURI().toURL()});
        List<File> classFiles = getClassFiles(outDir);
        List<Class<?>> classes = new ArrayList<>();
        for (File classFile : classFiles) {
            String className = getClassName(outDir, classFile);
            if (className.startsWith("org.vmj.model.")) {
                System.out.println("Loading class: " + className); // Debugging
                classes.add(classLoader.loadClass(className));
            }
        }

        classLoader.close();
        return classes;
    }

    private static List<File> getClassFiles(File directory) {
        List<File> classFiles = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        classFiles.addAll(getClassFiles(file)); // Recursive search
                    } else if (file.getName().endsWith(".class")) {
                        classFiles.add(file);
                    }
                }
            }
        }
        return classFiles;
    }

    private static String getClassName(File root, File classFile) {
        String relativePath = root.toPath().relativize(classFile.toPath()).toString();
        return relativePath
                .replace(File.separator, ".")
                .replace(".class", "");
    }
}

