package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {
    Page<Product> getAllProducts(Pageable Pageable);

    Product getProductById(Long id);

    Page<Product> searchProducts(String productName, String description, Integer minPrice, Integer maxPrice, Pageable pageable);

    Page<Product> searchProductsByCategory(Long categoryId, String productName, Integer minPrice, Integer maxPrice, Pageable pageable);

    Page<Product> getProductsOnSale(Pageable pageable);

    Product addOrSaveProduct(Product product);
    boolean deleteProduct(Long id);
}
