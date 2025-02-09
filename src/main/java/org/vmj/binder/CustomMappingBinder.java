//package org.vmj.binder;
//
//import org.hibernate.boot.jaxb.internal.MappingBinder;
//import org.hibernate.boot.jaxb.spi.Binding;
//import org.hibernate.boot.jaxb.spi.XmlSource;
//import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
//import org.hibernate.mapping.Property;
//import org.hibernate.mapping.RootClass;
//import org.vmj.mapping.CustomProperty;
//import org.xml.sax.EntityResolver;
//import org.xml.sax.InputSource;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//public class CustomMappingBinder extends MappingBinder {
//
//    public CustomMappingBinder(ClassLoaderService classLoaderService, boolean validateXml) {
//        super(classLoaderService, validateXml);
//    }
//
//    @Override
//    public Binding bind(XmlSource xmlSource) {
//        // Override the EntityResolver for this XML source
//        EntityResolver originalResolver = xmlSource.getEntityResolver();
//        xmlSource.setEntityResolver(new EntityResolver() {
//            @Override
//            public InputSource resolveEntity(String publicId, String systemId) {
//                if (systemId.contains("hibernate-mapping-3.0.dtd")) {
//                    try {
//                        return new InputSource(new FileInputStream("path/to/custom-hibernate-mapping-3.0.dtd"));
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException("Custom DTD not found", e);
//                    }
//                }
//                // Delegate to the original resolver if not our custom DTD
//                return originalResolver != null ? originalResolver.resolveEntity(publicId, systemId) : null;
//            }
//        });
//
//        // Proceed with binding
//        Binding binding = super.bind(xmlSource);
//
//        // Process custom attributes after binding
//        if (binding.getRoot() instanceof RootClass) {
//            RootClass rootClass = (RootClass) binding.getRoot();
//            for (Property property : rootClass.getProperties()) { // Use getProperties()
//                if (property instanceof CustomProperty) {
//                    CustomProperty customProperty = (CustomProperty) property;
//                    customProperty.setNewTag("yadada");
//                }
//            }
//        }
//
//        return binding;
//    }
//}