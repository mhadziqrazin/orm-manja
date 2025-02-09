package org.vmj.mapping;
import org.hibernate.mapping.Property;
public class CustomProperty extends Property {
    private String newTag;

    public String getNewTag() {
        return newTag;
    }

    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }
}
