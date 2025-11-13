package com.example.miniatures.repository;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MiniatureSaleRepository extends JpaRepository<MiniatureSale, Long> {
    Optional<MiniatureSale> findTopByClientNameOrderBySaleDateDesc(String clientName);
    List<MiniatureSale> findByClientName(MiniatureType type);
    List<MiniatureSale> findByType(MiniatureType type);
    List<MiniatureSale> findByScale(MiniatureScale scale);
    List<MiniatureSale> findByTypeAndScale(MiniatureType type, MiniatureScale scale);
    List<MiniatureSale> findByPriceGreaterThan(BigDecimal price);
    List<MiniatureSale> findByPriceBetween(BigDecimal minPrice,  BigDecimal maxPrice);
    List<MiniatureSale> findBySaleDateBetween(LocalDate start, LocalDate end);
    List<MiniatureSale> findTop10ByOrderBySaleDateDesc();

}
