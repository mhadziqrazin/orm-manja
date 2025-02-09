package org.vmj;


import org.vmj.model.Employee;

import java.io.FileWriter;
import java.lang.reflect.Field;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class HibernateMappingGenerator {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Employee.class;
            String mappingXml = generateMappingXml(clazz);

            System.out.println(mappingXml);

            try (FileWriter writer = new FileWriter(clazz.getSimpleName() + ".hbm.xml")) {
                writer.write(mappingXml);
            }
            System.out.println("Mapping file generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a simple Hibernate XML mapping from a given entity class.
     *
     * @param clazz The entity class to map.
     * @return A string containing the XML mapping.
     * @throws RuntimeException if the class is not annotated with @Entity or has no @Id.
     */
    public static String generateMappingXml(Class<?> clazz) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n");
        xml.append("<hibernate-mapping>\n");

        if (clazz.getAnnotation(Entity.class) == null) {
            throw new RuntimeException("Class " + clazz.getName() + " is not annotated with @Entity");
        }

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        String tableName = (tableAnnotation != null && !tableAnnotation.name().isEmpty())
                ? tableAnnotation.name()
                : clazz.getSimpleName().toUpperCase();

        xml.append("    <class name=\"")
                .append(clazz.getName())
                .append("\" table=\"")
                .append(tableName)
                .append("\">\n");

        boolean idFound = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
                xml.append("        <id name=\"")
                        .append(field.getName())
                        .append("\" type=\"")
                        .append(field.getType().getName())
                        .append("\">\n");

                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = (columnAnnotation != null && !columnAnnotation.name().isEmpty())
                        ? columnAnnotation.name()
                        : field.getName().toUpperCase();

                xml.append("            <column name=\"")
                        .append(columnName)
                        .append("\"/>\n");

                xml.append("            <generator class=\"native\"/>\n");
                xml.append("        </id>\n");
                break;
            }
        }
        if (!idFound) {
            throw new RuntimeException("No field annotated with @Id found in class " + clazz.getName());
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = (!columnAnnotation.name().isEmpty())
                        ? columnAnnotation.name()
                        : field.getName().toUpperCase();

                xml.append("        <property name=\"")
                        .append(field.getName())
                        .append("\" type=\"")
                        .append(field.getType().getName())
                        .append("\">\n");

                xml.append("            <column name=\"")
                        .append(columnName)
                        .append("\"/>\n");
                xml.append("        </property>\n");
            }
        }

        xml.append("    </class>\n");
        xml.append("</hibernate-mapping>\n");

        return xml.toString();
    }
}

