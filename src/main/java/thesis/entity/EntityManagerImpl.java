package thesis.entity;

import jakarta.persistence.*;
import thesis.annotation.Decorator;
import thesis.model.IDecorator;
import thesis.model.User;

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
    public <T> T create(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            if (!clazz.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Class must be an @Entity");
            }

            // decorator case
            Object baseEntity = null;
            IDecorator decorator = null;
            if (clazz.isAnnotationPresent(Decorator.class)) {
                List<String> baseColumnNames = new ArrayList<>();
                List<Object> baseValues = new ArrayList<>();

                Class<?> baseClass = clazz.getAnnotation(Decorator.class).base();
                Class<?> superClass = clazz.getSuperclass();

                decorator = (IDecorator) superClass.cast(entity);
                decorator.setRecord(create(decorator.getRecord()));

            }

            String tableName = clazz.getAnnotation(Table.class).name();
            Field[] fields = clazz.getDeclaredFields();
            List<String> columnNames = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (clazz.isAnnotationPresent(Decorator.class) && decorator != null) {
                columnNames.add("id");
                values.add(decorator.getId());
            }

            readFields(entity, fields, columnNames, values);

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
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void readFields(T entity, Field[] fields, List<String> columnNames, List<Object> values) throws IllegalAccessException {
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
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();

        try {
            String sql = sqlGetString(entityClass);
            System.out.println(sql);
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

                    // decorator case
                    if (entityClass.isAnnotationPresent(Decorator.class)) {
                        Class<?> baseClass = entityClass.getAnnotation(Decorator.class).base();
                        for (Field field : baseClass.getDeclaredFields()) {
                            if (field.isAnnotationPresent(Column.class)) {
                                field.setAccessible(true);
                                field.set(entity, rs.getObject(field.getAnnotation(Column.class).name()));
                            }
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

    private static <T> String sqlGetString(Class<T> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class must be an @Entity");
        }

        String tableName = entityClass.getAnnotation(Table.class).name();
        String sql = "SELECT * FROM " + tableName;

        if (entityClass.isAnnotationPresent(Decorator.class)) {
            Class<?> baseClass = entityClass.getAnnotation(Decorator.class).base();
            String baseTableName = baseClass.getAnnotation(Table.class).name();
            sql += " JOIN " + baseTableName + " ON " + baseTableName + ".id = " + tableName + ".id";
        }
        return sql;
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
