package org.vmj.model;

import jakarta.persistence.*;

@Entity // Ensure this annotation is present
@Table(name = "employee") // Optional, defines the table name
public class Employee {

    @Id // Define the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // Constructors
    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
