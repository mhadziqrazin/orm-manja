package org.vmj.model;


import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.DeltaEntity;

import org.vmj.annotation.*;
import java.math.BigDecimal;

@Entity
@Table(name = "basic_products")
@Setter
@Getter
public class BasicProduct extends Product {
    @Id
    private Long id;
    @Column(name="price")
    private BigDecimal price;

    @Column(name="category")
    private String category;

}
