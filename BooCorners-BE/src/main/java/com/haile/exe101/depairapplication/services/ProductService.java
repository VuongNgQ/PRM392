package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.repositories.ProductRepository;
import com.haile.exe101.depairapplication.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> searchProducts(String productName, String description, Integer minPrice, Integer maxPrice, Pageable pageable) {
        return productRepository.searchProducts(productName, description, minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Product> searchProductsByCategory(Long categoryId, String productName, Integer minPrice, Integer maxPrice, Pageable pageable) {
        return productRepository.searchProductsByCategory(categoryId, productName, minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Product> getProductsOnSale(Pageable pageable) {
        Date currentDate = new Date();
        return productRepository.findProductsOnSale(pageable, currentDate);
    }

    @Override
    public Product addOrSaveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setDeleted(false);
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
