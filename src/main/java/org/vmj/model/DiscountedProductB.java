package org.vmj.model;
import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.DeltaEntity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@DeltaEntity(core = Product.class)
@Entity
@Table(name = "discounted_products_b")
@Setter
@Getter
@PrimaryKeyJoinColumn(name = "id")
public class DiscountedProductB extends Product {
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "decorated_product_id", nullable = false)
//    private Product decoratedProduct;

    @Column(precision = 3, scale = 2)
    private BigDecimal discount;

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal(1);
    }
}
