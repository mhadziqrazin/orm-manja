//package org.vmj;
//
//
//import org.dom4j.Document;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.PersistentClass;
//import org.vmj.model.Employee;
//
//import java.util.Iterator;
//
//public class HibernateMappingExporterExample {
//
//    public static void main(String[] args) {
//        // Create a Hibernate Configuration and add your annotated class.
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(Employee.class);
//
//        // Optionally, you can configure database settings here or load a configuration file.
//        // For this example, we focus on mapping generation.
//
//        // Build the mappings. This creates the internal metadata (PersistentClass, etc.)
//        configuration.buildMappings();
//
//        // Iterate through all mapped classes.
//        Iterator<PersistentClass> classMappings = configuration.getClassMappings();
//
//        while (classMappings.hasNext()) {
//            PersistentClass persistentClass = classMappings.next();
//
//            // Get the XML Document (a dom4j Document) that represents the mapping.
//            // Note: This is produced internally when mappings are built.
//            Document mappingDoc = persistentClass.getDocument();
//
//            if (mappingDoc != null) {
//                // Convert the dom4j Document to an XML String.
//                String mappingXml = mappingDoc.asXML();
//
//                // For demonstration, print out the generated XML.
//                System.out.println("Mapping for " + persistentClass.getEntityName() + ":");
//                System.out.println(mappingXml);
//                System.out.println("--------------------------------------------------");
//            } else {
//                System.out.println("No XML document available for " + persistentClass.getEntityName());
//            }
//        }
//    }
//}
//
