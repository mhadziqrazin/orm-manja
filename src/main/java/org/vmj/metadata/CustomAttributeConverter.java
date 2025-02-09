//package org.vmj.metadata;
//
//import org.hibernate.boot.model.process.spi.MetadataBuildingContext;
//import org.hibernate.boot.spi.MetadataBuilderImplementor;
//import org.hibernate.mapping.RootClass;
//import org.vmj.mapping.CustomProperty;
//
//import javax.persistence.AttributeConverter;
//
//public class CustomAttributeConverter implements AttributeConverter {
//    @Override
//    public void convert(MetadataBuildingContext context, MetadataBuilderImplementor metadataBuilder) {
//        metadataBuilder.getMetadata().getEntityBindings().forEach(persistentClass -> {
//            if (persistentClass instanceof RootClass) {
//                RootClass rootClass = (RootClass) persistentClass;
//                rootClass.getProperties().forEach(property -> {
//                    String newTag = (String) property.getMetaAttribute("new_tag");
//                    if (newTag != null) {
//                        ((CustomProperty) property).setNewTag(newTag);
//                    }
//                });
//            }
//        });
//    }
//}
