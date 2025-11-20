package com.example.miniatures.dto.miniatureClient;

import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MiniatureSaleResponseDTO{

    //sale information
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDate saleDate;
    private MiniatureType type;
    private MiniatureScale scale;

    //Client information
    private Long clientId;
    private String clientName;
}
