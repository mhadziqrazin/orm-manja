package org.vmj;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class XsdGenerator {
    public static void main(String[] args) throws JAXBException, IOException {
        String outputDir = "E:/Kuliah/TA/orm-playground";
        String schemaFileName = "product-schema.xsd";
        Path outputPath = Paths.get(outputDir, schemaFileName);

        File dir = new File(outputDir);
        File file = outputPath.toFile();
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println(" Failed to create directory: " + dir.getAbsolutePath());
                return;
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Test data");
            System.out.println("Successfully wrote test data to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write test data: " + e.getMessage());
        }

        System.out.println("Writing XSD to: " + outputPath.toAbsolutePath());
        System.out.println("Created: " + dir.exists());
        System.out.println("File exists: " + dir.getAbsolutePath());

        JAXBContext context = JAXBContext.newInstance(Product.class);

        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) {
                try {
                    File file = outputPath.toFile();
                    return new StreamResult(file);
                } catch (Exception e) {
                    throw new RuntimeException("Error creating XSD file: " + e.getMessage(), e);
                }
            }
        });

        System.out.println("XSD Schema generated successfully: " + outputPath.toAbsolutePath());
    }

    @XmlRootElement
    @XmlType(propOrder = {"id", "name", "price"})
    public static class Product {
        public int id;
        public String name;
        public double price;
    }
}




