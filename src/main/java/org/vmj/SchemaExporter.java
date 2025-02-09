package org.vmj;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.schema.spi.DelayedDropRegistry;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;

import java.util.HashMap;
import java.util.Map;

public class SchemaExporter {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "none")  // Disable auto DDL execution
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(org.vmj.model.DiscountedProduct.class)  // Add entity class
                .buildMetadata();

        Map<String, Object> configurationValues = new HashMap<>();
        configurationValues.put("hibernate.format_sql", true);
        configurationValues.put("hibernate.hbm2ddl.auto", "create");  // Ensure schema creation


        SchemaManagementToolCoordinator.process(metadata, registry, configurationValues, null);

        StandardServiceRegistryBuilder.destroy(registry);
    }
}
