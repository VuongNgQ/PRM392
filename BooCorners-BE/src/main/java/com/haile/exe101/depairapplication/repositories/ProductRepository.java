package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(?1 IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', ?1, '%'))) AND " +
            "(?2 IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', ?2, '%'))) AND " +
            "(?3 IS NULL OR p.price >= ?3) AND " +
            "(?4 IS NULL OR p.price <= ?4) AND " +
            "p.isDeleted = false")
    Page<Product> searchProducts(String productName, String description, Integer minPrice, Integer maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND " +
            "(?2 IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', ?2, '%'))) AND " +
            "(?3 IS NULL OR p.price >= ?3) AND " +
            "(?4 IS NULL OR p.price <= ?4) AND " +
            "p.isDeleted = false")
    Page<Product> searchProductsByCategory(Long categoryId, String productName, Integer minPrice, Integer maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.sales s WHERE " +
            "s.startDate <= :currentDate AND s.endDate >= :currentDate AND " +
            "p.isDeleted = false")
    Page<Product> findProductsOnSale(Pageable pageable, Date currentDate);
}
