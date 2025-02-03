package org.vmj.listener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.vmj.annotation.DeltaEntity;
import org.vmj.model.Product;

import java.lang.reflect.Field;
public class DeltaInsertListener implements PreInsertEventListener {

    public static final DeltaInsertListener INSTANCE = new DeltaInsertListener();

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object entity = event.getEntity();
        DeltaEntity deltaAnnotation = entity.getClass().getAnnotation(DeltaEntity.class);

        if (deltaAnnotation != null) {
            try {
                Field productField = entity.getClass().getDeclaredField("product");
                productField.setAccessible(true);
                Product product = (Product) productField.get(entity);

                if (product == null || product.getId() == null) {
                    throw new IllegalStateException(
                            "Delta entity must reference an existing core entity with ID"
                    );
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid delta entity structure", e);
            }
        }
        return false;
    }
}
