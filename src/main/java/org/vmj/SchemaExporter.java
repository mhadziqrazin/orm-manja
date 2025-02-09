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
        // Step 1: Create Hibernate ServiceRegistry
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "none")  // Disable auto DDL execution
                .configure("hibernate.cfg.xml")
                .build();

        // Step 2: Build Metadata (from annotated entity classes)
        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(org.vmj.model.DiscountedProduct.class)  // Add entity class
                .buildMetadata();

        // Step 3: Define configuration values
        Map<String, Object> configurationValues = new HashMap<>();
        configurationValues.put("hibernate.format_sql", true);
        configurationValues.put("hibernate.hbm2ddl.auto", "create");  // Ensure schema creation

        // Step 4: Create a DelayedDropRegistry (this manages delayed schema drops)

        // Step 5: Execute SchemaManagementToolCoordinator.process()
        SchemaManagementToolCoordinator.process(metadata, registry, configurationValues, null);

        // Step 6: Cleanup Hibernate Registry
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
