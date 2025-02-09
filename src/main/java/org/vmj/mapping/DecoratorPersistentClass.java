package org.vmj.mapping;


import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

public class DecoratorPersistentClass extends PersistentClass {
    private Table table;
    private PersistentClass superclass;
    private int subclassId;
    public DecoratorPersistentClass(PersistentClass superclass, MetadataBuildingContext metadataBuildingContext) {
        super(metadataBuildingContext);
        this.superclass = superclass;
        this.subclassId = -1;
        System.out.println("### Creating DecoratorPersistentClass Instance ###");
    }

    @Override
    public String getClassName() {
        return super.getClassName() + " (Decorator)";
    }

    @Override
    public Table getTable() {
        return table;
    }
    @Override
    int nextSubclassId() {
        return -1;
    }

    public PersistentClass getSuperclass() {
        return superclass;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}

