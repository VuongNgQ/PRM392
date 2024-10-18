package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestBody {
    @NotNull(message = "Discount percentage is required")
    @Length(min = 0, max = 100, message = "Percentage must be between in range 0-100")
    private Integer discountPercentage;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;
}
