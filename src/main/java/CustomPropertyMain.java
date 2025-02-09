//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.RootClass;
//import org.hibernate.mapping.Property;
//import org.hibernate.boot.jaxb.spi.XmlSource;
//import org.hibernate.boot.jaxb.internal.MappingBinder;
//import org.vmj.binder.CustomMappingBinder;
//import org.vmj.mapping.CustomDTDResolver;
//import org.vmj.mapping.CustomProperty;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//import java.io.File;
//
//public class CustomPropertyMain {
//    public static void main(String[] args) {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//
//        MetadataSources sources = new MetadataSources(registry);
//        sources.addResource("com/example/Employee.hbm.xml");
//
//        // Attach the EntityResolver
//        sources.getXmlMappingBinderAccess();
//        sources.getXmlBinder().setEntityResolver(new CustomDTDResolver());
//
//        // Build metadata
//        var metadata = sources.buildMetadata();
//
//        // Access custom attributes
//        metadata.getEntityBindings().forEach(persistentClass -> {
//            persistentClass.getProperties().forEach(property -> {
//                Object newTag = property.getMetaAttribute("new_tag");
//                if (newTag != null) {
//                    System.out.println("Custom Attribute Value: " + newTag); // Output: yadada
//                }
//            });
//        });
//    }
//}
