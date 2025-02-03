package org.vmj.mapping;

import org.vmj.annotation.DecoratorStrategy;

import javax.persistence.JoinColumn;

public class DecoratorInheritanceStrategy implements InheritanceStrategy {

    @Override
    public void configure(EntityMappingDefinition definition) {
        DecoratorStrategy decoratorConfig = definition.getEntityClass()
                .getAnnotation(DecoratorStrategy.class);

        if (decoratorConfig != null) {
            // Configure join columns and relationships
            JoinColumn joinColumn = new JoinColumn();
            joinColumn.setName(decoratorConfig.joinColumn());
            joinColumn.setReferencedColumnName("id");

            definition.addJoin(joinColumn, decoratorConfig.targetEntity());
        }
    }
}
