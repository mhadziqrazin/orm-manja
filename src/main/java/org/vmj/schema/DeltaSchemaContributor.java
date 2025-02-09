//package org.vmj.schema;
//import org.hibernate.boot.MetadataBuilder;
//import org.hibernate.boot.model.relational.AuxiliaryDatabaseObject;
//import org.hibernate.boot.spi.MetadataBuilderContributor;
//
//import javax.persistence.AttributeConverter;
//
//import org.hibernate.dialect.Dialect;
//import org.hibernate.dialect.PostgreSQLDialect;
//import org.hibernate.mapping.Column;
//import org.hibernate.mapping.ForeignKey;
//import org.hibernate.mapping.Table;
//
//public class DeltaSchemaContributor implements MetadataBuilderContributor {
//
//    @Override
//    public void contribute(MetadataBuilder metadataBuilder) {
//        metadataBuilder.applyAuxiliaryDatabaseObject(
//                new AuxiliaryDatabaseObject() {
//                    @Override
//                    public String getExportIdentifier() {
//                        return "DeltaSchemaContributor:fk_product_id";
//                    }
//
//                    @Override
//                    public String[] sqlCreateStrings(Dialect dialect) {
//                        if (dialect instanceof PostgreSQLDialect) {
//                            return new String[]{
//                                    // Add custom foreign key with ON DELETE CASCADE for PostgreSQL
//                                    "ALTER TABLE discounted_products " +
//                                            "ADD CONSTRAINT fk_product_id " +
//                                            "FOREIGN KEY (product_id) REFERENCES products(id) " +
//                                            "ON DELETE CASCADE"
//                            };
//                        }
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] sqlDropStrings(Dialect dialect) {
//                        return new String[]{
//                                "ALTER TABLE discounted_products DROP CONSTRAINT IF EXISTS fk_product_id"
//                        };
//                    }
//
//                    @Override
//                    public boolean appliesToDialect(Dialect dialect) {
//                        return dialect instanceof PostgreSQLDialect;
//                    }
//
//                    @Override
//                    public boolean beforeTablesOnCreation() {
//                        // Return false if the SQL should be executed after the tables are created.
//                        return false;
//                    }
//                }
//        );
//    }
//}
