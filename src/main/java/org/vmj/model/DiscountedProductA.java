package org.vmj.model;
import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.DecoratorStrategy;
import org.vmj.annotation.DeltaEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@DeltaEntity(core = Product.class)
@Entity
@Table(name = "discounted_products_a")
@Setter
@Getter
@DecoratorStrategy(targetEntity = Product.class)
public class DiscountedProductA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "decorated_entity_id")
    private Long productId;

    private BigDecimal discountPercentage;
}
