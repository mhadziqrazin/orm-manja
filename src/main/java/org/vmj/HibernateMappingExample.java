//package org.vmj;
//
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.spi.MetadataBuildingContext;
//import org.hibernate.boot.spi.MetadataImplementor;
//import org.hibernate.id.IdentityGenerator;
//import org.hibernate.mapping.*;
//
//import java.io.FileWriter;
//import java.util.Iterator;
//
//public class HibernateMappingExample {
//
//    // Inner class that demonstrates using Hibernate's actual mapping classes.
//    public static class HibernateMappingGenerator {
//
//        // Hibernate classes used for mapping.
//        private RootClass employeeMapping;
//        private final MetadataBuildingContext buildingContext;
//
//        // Constructor receives the MetadataBuildingContext.
//        public HibernateMappingGenerator(MetadataBuildingContext buildingContext) {
//            this.buildingContext = buildingContext;
//        }
//
//        public void createEmployeeMapping() {
//            // Create a RootClass using the MetadataBuildingContext.
//            employeeMapping = new RootClass(buildingContext);
//            employeeMapping.setClassName("com.example.Employee");
//            // For simplicity, we use a String for the table name.
//            employeeMapping.setTable(new Table("employees"));
//
//            // Define the identifier (primary key) using Hibernate's SimpleValue.
//            SimpleValue idValue = new SimpleValue(buildingContext);
//            idValue.setTypeName("long");
//            idValue.addColumn(new Column("employee_id"));
//            idValue.setIdentifierGeneratorStrategy("assigned");
//
//            // Add properties (fields).
//            addProperty("firstName", "first_name", "string");
//            addProperty("lastName", "last_name", "string");
//        }
//
//        private void addProperty(String name, String column, String type) {
//            Property property = new Property();
//            property.setName(name);
//            SimpleValue value = new SimpleValue(buildingContext);
//            value.setTypeName(type);
//            value.addColumn(new Column(column));
//            property.setValue(value);
//            employeeMapping.addProperty(property);
//        }
//
//        // Generate XML from Hibernate's metamodel (simplified version).
//        public String generateMappingXml() {
//            StringBuilder xml = new StringBuilder();
//            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//            xml.append("<!DOCTYPE hibernate-mapping PUBLIC\n")
//                    .append("  \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n")
//                    .append("  \"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd\">\n");
//            xml.append("<hibernate-mapping>\n");
//
//            // Add <class> element.
//            xml.append("  <class name=\"")
//                    .append(employeeMapping.getClassName())
//                    .append("\" table=\"")
//                    .append(employeeMapping.getTable())
//                    .append("\">\n");
//
//            // Add <id> element.
//            xml.append("    <id name=\"id\" column=\"employee_id\">\n")
//                    .append("      <generator class=\"identity\"/>\n")
//                    .append("    </id>\n");
//
//            // Add <property> elements.
//            for (Iterator it = employeeMapping.getPropertyClosureIterator(); it.hasNext(); ) {
//                Object o = it.next();
//                if (o instanceof Property) {
//                    Property prop = (Property) o;
//                    // Retrieve the column name from the property's value.
//                    Column col = (Column) prop.getValue().getColumnIterator().next();
//                    xml.append("    <property name=\"")
//                            .append(prop.getName())
//                            .append("\" column=\"")
//                            .append(col.getName())
//                            .append("\" type=\"")
//                            .append(prop.getType().getName())
//                            .append("\"/>\n");
//                }
//            }
//
//            xml.append("  </class>\n");
//            xml.append("</hibernate-mapping>");
//            return xml.toString();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        // Build the StandardServiceRegistry and set the dialect.
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
//                .build();
//
//        // Build MetadataSources. (In this example we don't add any classes because
//        // we are building the mapping manually.)
//        MetadataSources metadataSources = new MetadataSources(registry);
//        Metadata metadata = metadataSources.buildMetadata();
//
//        // Obtain the MetadataBuildingContext from the MetadataImplementor.
//        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata;
//        MetadataBuildingContext buildingContext = metadataImplementor.getTypeConfiguration().getMetadataBuildingContext();
//
//        // Create our mapping generator with the buildingContext.
//        HibernateMappingGenerator generator = new HibernateMappingGenerator(buildingContext);
//        generator.createEmployeeMapping();
//
//        // Generate XML and save to a file.
//        String xml = generator.generateMappingXml();
//        try (FileWriter writer = new FileWriter("Employee.hbm.xml")) {
//            writer.write(xml);
//        }
//
//        System.out.println("Generated XML:\n" + xml);
//
//        // Clean up the registry.
//        StandardServiceRegistryBuilder.destroy(registry);
//    }
//}
