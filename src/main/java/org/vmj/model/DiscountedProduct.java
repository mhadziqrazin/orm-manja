package org.vmj.model;
import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.DeltaEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@DeltaEntity(core = Product.class)
@Entity
@Table(name = "discounted_products")
@Setter
@Getter
@PrimaryKeyJoinColumn(name = "id")
public class DiscountedProduct extends Product implements ProductDecorator {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decorated_product_id", nullable = false)
    private Product decoratedProduct;

    @Column(precision = 3, scale = 2)
    private BigDecimal discount;

    @Override
    public Product getDecoratedProduct() {
        return decoratedProduct;
    }

    @Override
    public void setDecoratedProduct(Product product) {
        this.decoratedProduct = product;
    }

    @Override
    public BigDecimal getPrice() {
        return decoratedProduct.getPrice().multiply(
                BigDecimal.ONE.subtract(discount)
        );
    }
}
