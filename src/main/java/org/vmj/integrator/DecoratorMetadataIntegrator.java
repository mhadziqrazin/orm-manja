//package org.vmj.integrator;
//
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.model.source.internal.annotations.AnnotationMetadataSourceProcessorImpl;
//import org.hibernate.boot.spi.MetadataBuildingContext;
//import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.integrator.spi.Integrator;
//import org.hibernate.service.spi.SessionFactoryServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.mapping.PersistentClass;
//import org.hibernate.mapping.RootClass;
//import org.vmj.annotation.DeltaEntity;
//import org.vmj.mapping.DecoratorPersistentClass;
//
//public class DecoratorMetadataIntegrator implements Integrator {
//
//    @Override
//    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
//        System.out.println("### DecoratorMetadataIntegrator: Hibernate Metadata Integration Started ###");
//        MetadataBuildingContext buildingContext = (MetadataBuildingContext) metadata;
//
//        for (PersistentClass entity : metadata.getEntityBindings()) {
//            System.out.println("Entity: " + entity.getClass().getSimpleName() + " -> " + entity.getClass().getSuperclass().getSimpleName());
//
//            // Detect if it's a decorator (based on your custom logic)
//            if (isDecorator(entity)) {
//                System.out.println("Detected decorator class: " + entity.getClass().getSimpleName());
//                // Modify metadata to treat it differently if necessary
//                DecoratorPersistentClass decoratorClass = new DecoratorPersistentClass(buildingContext);
//                decoratorClass.setClassName(entity.getClassName());
//                decoratorClass.setTable(entity.getTable());
//
//                // Replace in Metadata
//                metadata.getEntityBindings().remove(entity);
//                metadata.getEntityBindings().add(decoratorClass);
//
//                System.out.println("✅ Converted to DecoratorPersistentClass: " + entity.getClassName());
//            }
//        }
//    }
//
//    private boolean isDecorator(PersistentClass entity) {
//        // Example: Check if the class has a specific annotation (e.g., @Decorator)
//        return entity.getMappedClass().isAnnotationPresent(DeltaEntity.class);
//    }
//
//    @Override
//    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
//        // Clean up if necessary
//    }
//}
//
