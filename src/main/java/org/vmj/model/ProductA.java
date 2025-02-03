package org.vmj.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products_a")
public class ProductA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
}