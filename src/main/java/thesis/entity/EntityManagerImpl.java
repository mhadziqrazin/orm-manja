package thesis.entity;

import jakarta.persistence.*;
import thesis.annotation.Decorator;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager {
    private final Connection connection;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> void create(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            if (!clazz.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Class must be an @Entity");
            }

            String tableName = clazz.getAnnotation(Table.class).name();
            Field[] fields = clazz.getDeclaredFields();
            List<String> columnNames = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            for (Field field : fields) {
                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    columnNames.add(field.getAnnotation(Column.class).name());
                    values.add(field.get(entity));
                }
            }

            // use "?" to secure from sql injection
            String sql = "INSERT INTO " + tableName + " (" + String.join(", ", columnNames) + ") VALUES (" +
                    "?, ".repeat(values.size()).replaceAll(", $", "") + ")";

            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < values.size(); i++) {
                    // sql key indexing 1 base
                    stmt.setObject(i + 1, values.get(i));
                }

                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    Field idField = getIdField(clazz);
                    idField.setAccessible(true);
                    idField.set(entity, rs.getObject(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();

        try {
            if (!entityClass.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Class must be an @Entity");
            }

            String tableName = entityClass.getAnnotation(Table.class).name();
            String sql = "SELECT * FROM " + tableName;
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance();
                    for (Field field : entityClass.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(field.getAnnotation(Column.class).name()));
                        }
                    }
                    result.add(entity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Field getIdField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No @Id field found in entity class");
    }
}
