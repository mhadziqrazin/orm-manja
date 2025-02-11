package org.vmj.mapping;

import org.vmj.annotation.Column;
import org.vmj.annotation.Entity;
import org.vmj.annotation.Id;

import java.lang.reflect.Field;

public class Mapping {
    public static String getConfigurationFileName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase() + ".hbm.xml";
    }

    public static String getTableName(Class<?> clazz) {
        return getSelectedName(
                clazz.getSimpleName().toLowerCase(),
                clazz.getAnnotation(Entity.class).table()
        );
    }

    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Id.class))
            return getIdColumnName(field);

        return getSelectedName(
                field.getName(),
                field.getAnnotation(Column.class).name()
        );

    }

    private static String getIdColumnName(Field field) {
        return getSelectedName(
                field.getName(),
                field.getAnnotation(Id.class).name()
        );
    }

    private static String getSelectedName(String objectName, String customName) {
        if (customName.equals("")) return objectName;
        return customName;
    }
}
