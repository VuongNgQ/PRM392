package com.haile.exe101.depairapplication.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;  // Khóa chính cho bảng Sale

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;  // Mối quan hệ với bảng Product (nhiều Sale cho một Product)

    @Column(nullable = false)
    private Integer discountPercentage;  // Giảm giá dưới dạng phần trăm (%)

    @Column(nullable = false)
    private LocalDate startDate;  // Ngày bắt đầu của khuyến mãi

    @Column(nullable = false)
    private LocalDate endDate;    // Ngày kết thúc của khuyến mãi

    public boolean isActive() {
        // Compare today’s date with endDate to check if the sale is still active
        return LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate);
    }
}