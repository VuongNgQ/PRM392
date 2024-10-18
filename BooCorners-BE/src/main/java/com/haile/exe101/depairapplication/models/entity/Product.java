package com.haile.exe101.depairapplication.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Nationalized
    private String productName;

    @NotNull
    @Nationalized
    private String manufacturer;

    public Product(Long id, String productName, String manufacturer, Integer quantity, Category category, Integer price, String description, String imageName, boolean isDeleted, List<CartItem> cartItems, List<Sale> sales) {
        this.id = id;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageName = imageName;
        this.isDeleted = isDeleted;
        this.cartItems = cartItems;
        this.sales = sales;
    }

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @JsonBackReference
    private Category category;

    private Integer price;

    @Nationalized
    @Length(max = 9999)
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private String imageName;

    private boolean isDeleted;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

    // One-to-Many relationship with Sale
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Sale> sales;
}
