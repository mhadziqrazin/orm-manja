package org.vmj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DecoratorStrategy {
    Class<?> targetEntity();
    String joinColumn() default "decorated_entity_id";
}
