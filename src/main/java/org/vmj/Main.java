package org.vmj;
import org.vmj.model.BasicProduct;
import org.vmj.model.DiscountedProduct;
import org.vmj.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("deltaPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Create core entity
        // Create basic product
        BasicProduct laptop = new BasicProduct();
        laptop.setName("Premium Laptop");
        laptop.setPrice(new BigDecimal("1999.99"));
        em.persist(laptop);
        em.flush();
// Create discount decorator
        DiscountedProduct discountedLaptop = new DiscountedProduct();
        discountedLaptop.setName("Discounted Laptop");
        discountedLaptop.setDecoratedProduct(laptop);
        discountedLaptop.setDiscount(new BigDecimal("0.15"));
        System.out.println(discountedLaptop);
        em.persist(discountedLaptop);

// Create tax decorator (decorating the discounted product)
//        TaxedProduct finalProduct = new TaxedProduct();
//        finalProduct.setName("Final Product");
//        finalProduct.setDecoratedProduct(discountedLaptop);
//        finalProduct.setTaxRate(new BigDecimal("0.10"));
//        em.persist(finalProduct);
//        em.persist(discounted);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}