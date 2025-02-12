package thesis.entity;

import java.util.List;

public interface EntityManager {
    <T> void create(T entity);
//    <T> T find(Class<T> entityClass, Object primaryKey);
//    <T> void remove(T entity);
//    <T> List<T> findAll(Class<T> entityClass);
}
