package org.vmj;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.tool.schema.Action;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.vmj.integrator.DecoratorMetadataIntegrator;
import org.vmj.model.Employee;

public class HbmXmlExporter {

    public static <ExecutionOptions> void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.temp.use_jdbc_metadata_defaults", "false")
                .configure("hibernate.cfg.xml")
                .applySetting("hibernate.integrator_provider", DecoratorMetadataIntegrator.class)
                .build();

        MetadataSources metadataSources = new MetadataSources(registry);
//        metadataSources.addPackage("org.vmj");
//        metadataSources.addAnnotatedClass(org.vmj.model.Employee.class);
        metadataSources.addAnnotatedClass(org.vmj.model.DiscountedProduct.class);

        Metadata metadata = metadataSources.buildMetadata();
//        Map<String, Object> properties = new HashMap<>( sessionFactory.getProperties() );
//        SchemaManagementToolCoordinator.process(
//                metadata,
//                reg,
//                properties,
//                action -> {}
//        );
        int count = 0;
        DecoratorMetadataIntegrator integrator = new DecoratorMetadataIntegrator();
        integrator.integrate(metadata, (SessionFactoryImplementor) null, null);
        System.out.println("Entity: " + metadata.getEntityBindings());
        for (PersistentClass pc : metadata.getEntityBindings()) {
            String mappingXml = generateMappingXml(pc);

            String fullClassName = pc.getClassName();
            String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            String fileName = simpleName + ".hbm.xml";

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(mappingXml);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Generated mapping file: " +
                    Paths.get(fileName).toAbsolutePath());
            count++;
        }

        if (count == 0) {
            System.out.println("No entity bindings were found. " +
                    "Make sure your annotated classes are scanned properly.");
        }

        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Generates a basic hbm.xml mapping string from a PersistentClass.
     */
    private static String generateMappingXml(PersistentClass pc) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<!DOCTYPE hibernate-mapping PUBLIC\n")
                .append("  \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n")
                .append("  \"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd\">\n")
                .append("<hibernate-mapping>\n");

        xml.append("  <class name=\"")
                .append(pc.getClassName())
                .append("\" table=\"")
                .append(pc.getTable().getName())
                .append("\">\n");

        if (pc.getIdentifierProperty() != null) {
            Property idProp = pc.getIdentifierProperty();
            xml.append("    <id name=\"")
                    .append(idProp.getName())
                    .append("\" type=\"")
                    .append(idProp.getType().getName())
                    .append("\">\n");

            Value idValue = idProp.getValue();
            List<?> idColumns = idValue.getColumns();
            if (idColumns != null && !idColumns.isEmpty()) {
                Column col = (Column) idColumns.get(0);
                xml.append("      <column name=\"")
                        .append(col.getName())
                        .append("\"/>\n");
            }
            xml.append("      <generator class=\"identity\"/>\n");
            xml.append("    </id>\n");
        }


        for (Property prop : pc.getPropertyClosure()) {
            xml.append("    <property name=\"")
                    .append(prop.getName())
                    .append("\" type=\"")
                    .append(prop.getType().getName())
                    .append("\"");

            Value value = prop.getValue();
            List<?> columns = value.getColumns();
            if (columns != null && !columns.isEmpty()) {
                Column col = (Column) columns.get(0);
                xml.append(" column=\"")
                        .append(col.getName())
                        .append("\"");
            }
            xml.append("/>\n");
        }

        xml.append("  </class>\n")
                .append("</hibernate-mapping>\n");

        return xml.toString();
    }
}
