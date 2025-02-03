package org.vmj.mapping;

import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.PersistentClass;

/**
 * A custom mapping class to support the Decorator Pattern.
 * Unlike a normal subclass mapping (e.g., SingleTableSubclass or JoinedSubclass),
 * this decorator mapping does not result in a new row in the database.
 * Instead, it decorates (adds behavior to) an existing persistent entity.
 */
public abstract class Decorator extends PersistentClass {

    private PersistentClass coreClass;
    private final int decoratorId;

    /**
     * Constructs a new DecoratorSubclass mapping.
     *
     * @param coreClass the persistent class that represents the core entity to be decorated.
     * @param buildingContext the metadata building context.
     */
    public Decorator(PersistentClass coreClass, MetadataBuildingContext buildingContext) {
        super(buildingContext);
        this.coreClass = coreClass;
        // Instead of generating a new subclass id, we want to share the same id as the core.
        // Here we simply copy the core’s subclass id.
        this.decoratorId = coreClass.getSubclassId();
    }

    @Override
    public int nextSubclassId() {
        // In a decorator strategy, we do not increment the subclass id;
        // we always use the core entity’s identifier.
        return getCoreClass().getSubclassId();
    }


    @Override
    public int getSubclassId() {
        return decoratorId;
    }


    @Override
    public String getNaturalIdCacheRegionName() {
        // Delegate to the core class.
        return coreClass.getNaturalIdCacheRegionName();
    }

    @Override
    public String getCacheConcurrencyStrategy() {
        // Delegate to the root class (the core).
        return coreClass.getRootClass().getCacheConcurrencyStrategy();
    }

    /**
     * In a decorator strategy, we do not generate additional insert statements
     * because the decorator uses the same row as the core. This method can be
     * overridden in your custom persister to avoid creating a new row.
     */
    public boolean isDecorator() {
        return true;
    }

    // Additional overrides could include:
    // - Overriding the SQL generation methods to avoid generating a separate table
    // - Adjusting the property mapping so that the decorator’s additional fields are
    //   merged with the core’s columns (or placed in a secondary table if desired)
    // - Customizing the persister creation to convert a "persist" call into an "update"
    //   on the core row when a decorated entity is saved.

    // Getters and setters for the coreClass if needed
    public PersistentClass getCoreClass() {
        return coreClass;
    }
}
