package org.vmj.parser;


import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.vmj.annotation.Entity;
import org.vmj.annotation.Column;
import org.vmj.annotation.Id;

public class XMLMappingGenerator {
    public static void generate(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not an entity.");
        }

        String xmlContent = generateXMLContent(clazz);
        saveToFile(clazz, xmlContent);
    }

    private static String generateXMLContent(Class<?> clazz) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
        xml.append("<entity-mapping>\n");
        xml.append(String.format("    <entity name=\"%s\">\n", clazz.getSimpleName()));

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                xml.append(String.format("        <id name=\"%s\" type=\"%s\"/>\n", field.getName(), field.getType().getSimpleName()));
            } else if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                xml.append(String.format("        <column name=\"%s\" type=\"%s\"/>\n",
                        column.name().isEmpty() ? field.getName() : column.name(),
                        field.getType().getSimpleName()));
            }
        }

        xml.append("    </entity>\n");
        xml.append("</entity-mapping>");
        return xml.toString();
    }

    private static void saveToFile(Class<?> clazz, String content) {
        String directory = "src/main/resources/mappings/";
        createDirectory(directory);

        String fileName = directory + clazz.getSimpleName() + ".xml";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("XML mapping file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory(String directory) {
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

