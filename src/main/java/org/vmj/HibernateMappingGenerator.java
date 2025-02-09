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
            // Specify the class to map – in this example, Employee.class
            Class<?> clazz = Employee.class;
            String mappingXml = generateMappingXml(clazz);

            // Print the generated XML to the console
            System.out.println(mappingXml);

            // Optionally, write the mapping to a file named "Employee.hbm.xml"
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

        // XML header and DOCTYPE declaration
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n");
        xml.append("<hibernate-mapping>\n");

        // Ensure the class is an entity
        if (clazz.getAnnotation(Entity.class) == null) {
            throw new RuntimeException("Class " + clazz.getName() + " is not annotated with @Entity");
        }

        // Get the table name from @Table, if present; otherwise use the class name in uppercase.
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        String tableName = (tableAnnotation != null && !tableAnnotation.name().isEmpty())
                ? tableAnnotation.name()
                : clazz.getSimpleName().toUpperCase();

        // Begin the <class> element
        xml.append("    <class name=\"")
                .append(clazz.getName())
                .append("\" table=\"")
                .append(tableName)
                .append("\">\n");

        // Process the fields: first find the field annotated with @Id
        boolean idFound = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
                xml.append("        <id name=\"")
                        .append(field.getName())
                        .append("\" type=\"")
                        .append(field.getType().getName())
                        .append("\">\n");

                // Get the column name from @Column if available; else default to uppercase field name.
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = (columnAnnotation != null && !columnAnnotation.name().isEmpty())
                        ? columnAnnotation.name()
                        : field.getName().toUpperCase();

                xml.append("            <column name=\"")
                        .append(columnName)
                        .append("\"/>\n");

                // Here we use a simple "native" generator for the identifier.
                xml.append("            <generator class=\"native\"/>\n");
                xml.append("        </id>\n");
                break; // Assuming only one identifier field for simplicity.
            }
        }
        if (!idFound) {
            throw new RuntimeException("No field annotated with @Id found in class " + clazz.getName());
        }

        // Process remaining fields that are annotated with @Column as simple properties.
        for (Field field : clazz.getDeclaredFields()) {
            // Skip the field already processed as @Id.
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

        // End the <class> element and the mapping.
        xml.append("    </class>\n");
        xml.append("</hibernate-mapping>\n");

        return xml.toString();
    }
}

