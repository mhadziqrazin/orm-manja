package thesis.model;

import jakarta.persistence.*;
import thesis.annotation.Decorator;

@Entity()
@Table(name = "students")
@Decorator(base = UserComponent.class)
public class StudentComponent extends UserDecorator {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private String level;

    public StudentComponent() {}

    public StudentComponent(UserComponent user, String level) {
        super(user);
        this.level = level;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return super.toString() + ", level: " + level;
    }
}
