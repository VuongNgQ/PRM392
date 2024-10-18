package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.Category;
import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.models.request.ProductRequest;
import com.haile.exe101.depairapplication.models.response.BasePageResponse;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.CategoryService;
import com.haile.exe101.depairapplication.services.interfaces.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Search products with filters",
            description = "Search products by name, description, min/max price with pagination and sort")
    @GetMapping
    public ResponseEntity<BasePageResponse<Product>> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Xử lý sort
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        // Tạo pageable
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Gọi service tìm kiếm
        Page<Product> productsPage = productService.searchProducts(productName, description, minPrice, maxPrice, pageable);
        long number = productsPage.getTotalElements();

        return ResponseEntity.ok(new BasePageResponse<>(200, "Retrieved " + number + " products", productsPage, null));
    }

    @Operation(summary = "Get products by id",
            description = "Get products by id")
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<Product>> getProductById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(new BaseResponse<>(200, "Get product by id successfully", null, productService.getProductById(id)));
    }

    @Operation(summary = "Search products by category with filters",
            description = "Search products by category, name, min/max price with pagination and sort")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BasePageResponse<Product>> searchProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Xử lý sort
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        // Tạo pageable
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Gọi service tìm kiếm theo category
        Page<Product> productsPage = productService.searchProductsByCategory(categoryId, productName, minPrice, maxPrice, pageable);
        long number = productsPage.getTotalElements();

        return ResponseEntity.ok(new BasePageResponse<>(200, "Retrieved " + number + " products", productsPage, null));
    }

    @Operation(summary = "Get all products on sale with paging and sort",
            description = "Get all products currently on sale with pagination and sorting")
    @GetMapping("/on-sale")
    public ResponseEntity<BasePageResponse<Product>> getProductsOnSale(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Process sort information
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Fetch products on sale
        Page<Product> productsOnSalePage = productService.getProductsOnSale(pageable);
        long number = productsOnSalePage.getTotalElements();

        return ResponseEntity.ok(new BasePageResponse<>(200, "Retrieved " + number + " products on sale", productsOnSalePage, null));
    }

    @Operation(summary = "Create a product",
            description = "Create a product by inputting product information and price")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Product>> createProduct(@RequestBody @Valid ProductRequest product) {
        try {
            Product newProduct = new Product();

            newProduct.setProductName(product.getProductName());
            newProduct.setManufacturer(product.getManufacturer());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescription(product.getDescription());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setImageName(product.getImageName());
            newProduct.setDeleted(false);

            Category c = categoryService.getCategoryById(product.getCategoryId());
            if (c != null) {
                newProduct.setCategory(c);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Product category is not found", null, null));
            }

            Product createdProduct = productService.addOrSaveProduct(newProduct);

            return ResponseEntity.ok(new BaseResponse<>(200, "Product created successfully", null, createdProduct));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Product create failed: " + ex.getMessage(), null, null));
        }
    }

    @Operation(summary = "Update a product",
            description = "Update an existing product by inputting product information and price")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Product>> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest product) {
        try {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct != null) {
                existingProduct.setProductName(product.getProductName());
                existingProduct.setManufacturer(product.getManufacturer());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setImageName(product.getImageName());

                Product updatedProduct = productService.addOrSaveProduct(existingProduct);

                return ResponseEntity.ok(new BaseResponse<>(200, "Product updated successfully", null, updatedProduct));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Product is not found", null, null));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Product update failed: " + ex.getMessage(), null, null));
        }
    }

    @Operation(summary = "Delete a product",
            description = "Delete an existing product by inputting product information and price")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Product>> deleteProduct(@PathVariable("id") Long id) {
        try {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct != null) {
                productService.deleteProduct(id);
                return ResponseEntity.ok(new BaseResponse<>(200, "Product deleted successfully", null, existingProduct));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Product is not found", null, null));
            }
        } catch (Exception ex) {
            return ResponseEntity.ok(new BaseResponse<>(500, "Product delete failed: " + ex.getMessage() , null, null));
        }
    }
}
