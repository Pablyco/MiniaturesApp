package com.example.miniatures.model;

import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MiniatureSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String clientName;
    private BigDecimal price;
    private LocalDate saleDate;

    @Enumerated(EnumType.STRING)
    private MiniatureType type;

    @Enumerated(EnumType.STRING)
    private MiniatureScale scale;

}
