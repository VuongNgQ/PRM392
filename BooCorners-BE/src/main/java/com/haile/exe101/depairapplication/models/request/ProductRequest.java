package com.haile.exe101.depairapplication.models.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haile.exe101.depairapplication.models.entity.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product Name is required")
    @NotBlank(message = "Product Name is not empty")
    @Nationalized
    private String productName;

    @NotNull(message = "Manufacturer is required")
    @NotBlank(message = "Manufacturer is not empty")
    @Nationalized
    private String manufacturer;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be above zero")
    private Integer quantity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be above zero")
    private Integer price;

    @Nationalized
    private String description;

    @URL(message = "Should be an URL of image")
    private String imageName;
}
