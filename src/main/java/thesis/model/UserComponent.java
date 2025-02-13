package thesis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserComponent implements User {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    // Constructors, Getters, Setters
    public UserComponent() {}

    public UserComponent(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", username: " + username;
    }
}
