package com.example.miniatures.dto.miniatureSale;

import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class SalesFilterDTO {

    MiniatureType type;
    MiniatureScale scale;

    @Positive(message = "Client ID should be greater than zero '0' ")
    Long clientId;

    @Positive(message = "Price should be greater than zero '0' ")
    BigDecimal minPrice;

    @Positive(message = "Price should be greater than zero '0' ")
    BigDecimal maxPrice;

    LocalDate startDate;
    LocalDate endDate;
}
