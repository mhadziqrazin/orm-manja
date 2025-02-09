//package org.vmj;
//
//import org.hibernate.cfg.Configuration;
//import org.hibernate.tool.hbm2x.Exporter;
//import org.hibernate.tool.hbm2x.hbm2hbmxml.HbmExporter;
//
//import java.io.File;
//
//public class HibernateXMLAppenderExample {
//    public static void main(String[] args) {
//        // Create and configure the Hibernate Configuration.
//        // (This assumes you have a hibernate.cfg.xml in your classpath.)
//        Configuration configuration = new Configuration().configure();
//
//        // Add your annotated classes (if not already listed in your cfg file).
//        configuration.addAnnotatedClass(com.example.Employee.class);
//
//        // Build the mappings.
//        configuration.buildMappings();
//        configuration.mapp
//
//        // Specify an output directory for the generated mapping files.
//        File outputDir = new File("generated-hbm");
//        if (!outputDir.exists()) {
//            outputDir.mkdirs();
//        }
//
//        // Create the HbmExporter (the XML appender from Hibernate Tools).
//        Exporter hbmExporter = new HbmExporter(configuration, outputDir);
//
//        // Optionally, you can set properties on the exporter.
//        // For example, hbmExporter.getProperties().setProperty("ejb3", "true");
//
//        // Start the exporter to generate the .hbm.xml files.
//        hbmExporter.start();
//
//        System.out.println("Hibernate Tools HbmExporter generated mapping files in: " + outputDir.getAbsolutePath());
//    }
//}
