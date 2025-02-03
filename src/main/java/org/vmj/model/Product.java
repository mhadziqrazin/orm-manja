package org.vmj.model;
import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.CoreEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@CoreEntity
@Entity
@Table(name = "products")
@Setter
@Getter // This should generate getId() if Lombok is configured properly
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Add this if Lombok isn't working
    public Long getId() {
        return id;
    }

    public abstract BigDecimal getPrice();
}
