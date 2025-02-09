//package org.vmj.binder;
//
//
//import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmProperty;
//import org.hibernate.boot.jaxb.internal.hbm.HbmBinder;
//import org.hibernate.boot.spi.MetadataBuildingContext;
//import org.hibernate.boot.jaxb.spi.BindingContext;
//import org.hibernate.boot.jaxb.internal.
//import org.hibernate.mapping.Property;
//import org.vmj.mapping.CustomProperty;
//
//public class CustomHbmBinder extends HbmBinder {
//    public CustomHbmBinder(MetadataBuildingContext buildingContext, BindingContext bindingContext) {
//        super(buildingContext, bindingContext);
//    }
//
//    @Override
//    protected Property createProperty(JaxbHbmProperty jaxbProperty) {
//        // Call the super binder to get the standard property
//        Property property = super.createProperty(jaxbProperty);
//
//        // Check if our new attribute is present. (Assume getNewTag() exists on the JAXB binding.)
//        String newTag = jaxbProperty.getNewTag();
//        if ( newTag != null ) {
//            // Either cast the property if it is already our custom type or create one
//            if ( property instanceof CustomProperty) {
//                ((CustomProperty) property).setNewTag(newTag);
//            }
//            else {
//                // Create a new CustomProperty and copy the existing property info
//                CustomProperty customProperty = new CustomProperty();
//                customProperty.setName(property.getName());
//                customProperty.setValue(property.getValue());
//                customProperty.setUpdateable(property.isUpdateable());
//                customProperty.setInsertable(property.isInsertable());
//                // ... (copy any other needed values)
//                customProperty.setNewTag(newTag);
//                property = customProperty;
//            }
//        }
//        return property;
//    }
//}
//
