package org.vmj.listener;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.vmj.model.Product;
import org.vmj.model.ProductDecorator;

import java.sql.PreparedStatement;

public class DecoratorValidationListener implements PreInsertEventListener {

    public static final DecoratorValidationListener INSTANCE = new DecoratorValidationListener();

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        System.out.println("yadadadada pre insert");
        Object entity = event.getEntity();
        System.out.println(entity);
        if (entity instanceof ProductDecorator) {
            Product decorated = ((ProductDecorator) entity).getDecoratedProduct();
            System.out.println(decorated);
            System.out.println("here decorated");
            validateDecoratedProduct(decorated);
//            String updateSql = "UPDATE products SET discount = ? WHERE id = ?";
//            event.getSession().doWork(connection -> {
//                try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
//                    ps.setBigDecimal(1, decorated.getPrice());
//                    ps.setLong(2, decorated.getId());
//                    ps.executeUpdate();
//                }
//            });
            // Returning true tells Hibernate to veto the insert event.
            return true;
        }
        return false;
    }

    private void validateDecoratedProduct(Product decorated) {
        if (decorated == null) {
            throw new IllegalStateException("Decorator must reference a product");
        }

        if (decorated.getId() == null) {
            throw new IllegalStateException(
                    "Decorated product must be persisted first. Detected transient: "
                            + decorated.getClass().getSimpleName()
            );
        }
    }
}
