package org.vmj.model;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Setter
@Getter // This should generate getId() if Lombok is configured properly
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private Long id;

    @Column(nullable = false)
    @XmlElement
    private String name;

    @Column(nullable = true)
    @XmlElement
    private String tag;

    // Add this if Lombok isn't working
    public Long getId() {
        return id;
    }

    public abstract BigDecimal getPrice();
}
