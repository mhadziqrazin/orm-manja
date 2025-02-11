package org.vmj;


import org.vmj.parser.ClassReader;
import org.vmj.parser.XMLMappingGenerator;

import java.util.List;

public class VanillaMapping {
    public static void main(String[] args) {
        try {
            String compiledClassDirectory = "build/classes/java/main"; // Update path
            List<Class<?>> loadedClasses = ClassReader.readClasses(compiledClassDirectory);

            System.out.println("Loaded Classes:");
            for (Class<?> clazz : loadedClasses) {
                System.out.println(" - " + clazz.getName());
                try {
                    XMLMappingGenerator.generate(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
