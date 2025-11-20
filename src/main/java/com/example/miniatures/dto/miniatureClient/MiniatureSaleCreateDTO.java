package com.example.miniatures.dto.miniatureClient;


import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MiniatureSaleCreateDTO {

    @NotBlank(message = "Miniature name cant be empty")
    String name;

    @NotNull(message = "Price is obligatory")
    @Positive(message = "Price should be greater than zero '0' ")
    BigDecimal price;

    @NotNull(message = "The sale date is obligatory")
    @PastOrPresent(message = "The sale date can't be assigned in the future")
    LocalDate saleDate;

    @NotNull(message = "Miniature type is obligatory")
    MiniatureType type;

    @NotNull(message = "Miniature scale is obligatory")
    MiniatureScale scale;

    @NotNull(message = "Client ID is obligatory")
    @Positive@Positive(message = "Client ID should be greater than zero '0' ")
    Long clientId;
}
