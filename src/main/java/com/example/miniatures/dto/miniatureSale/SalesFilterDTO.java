package com.example.miniatures.dto.miniatureSale;

import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Filter parameters for searching miniature sales")
public abstract class SalesFilterDTO {

    @Schema(description = "Filter by miniature type", example = "WARHAMMER")
    MiniatureType type;

    @Schema(description = "Filter by miniature scale", example = "THIRTY_TWO_MM")
    MiniatureScale scale;

    @Schema(description = "Filter by client ID", example = "1")
    @Positive(message = "Client ID should be greater than zero '0' ")
    Long clientId;

    @Schema(description = "Minimum price filter", example = "10.00")
    @Positive(message = "Price should be greater than zero '0' ")
    BigDecimal minPrice;

    @Schema(description = "Maximum price filter", example = "100.00")
    @Positive(message = "Price should be greater than zero '0' ")
    BigDecimal maxPrice;

    @Schema(description = "Start date filter", example = "2024-11-6")
    LocalDate startDate;

    @Schema(description = "Start date filter", example = "2025-8-2")
    LocalDate endDate;
}
