package org.vmj.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.vmj.annotation.DeltaEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Table(name = "basic_products")
@PrimaryKeyJoinColumn(name = "product_id")
@Setter
@Getter
public class BasicProduct extends Product {
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
