package com.example.miniatures.repository;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MiniatureSaleRepository extends JpaRepository<MiniatureSale, Long> , JpaSpecificationExecutor<MiniatureSale> {
    Optional<MiniatureSale> findTopByClientNameOrderBySaleDateDesc(String clientName);
    List<MiniatureSale> findTop10ByOrderBySaleDateDesc();
}
