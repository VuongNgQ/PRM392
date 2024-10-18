package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.models.entity.Sale;
import com.haile.exe101.depairapplication.models.request.SaleRequestBody;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.ProductService;
import com.haile.exe101.depairapplication.services.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create sale for product",
            description = "Add sale percentage for product")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{product-id}")
    public ResponseEntity<BaseResponse<Sale>> createSaleForProduct(
            @PathVariable("product-id") Long productId,
            @RequestBody @Valid SaleRequestBody saleRequestBody) {

        Product product1 = productService.getProductById(productId);
        if (product1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(404, "Product not found", null, null));
        }

        if (saleRequestBody.getStartDate().isAfter(saleRequestBody.getEndDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(400, "Start date must be before end date", null, null));
        }

        Sale sale = new Sale();
        sale.setProduct(product1);
        sale.setDiscountPercentage(saleRequestBody.getDiscountPercentage());
        sale.setStartDate(saleRequestBody.getStartDate());
        sale.setEndDate(saleRequestBody.getEndDate());

        Sale savedSale = saleService.createSale(sale);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(200, "Sale created successfully", null, savedSale));
    }
}
