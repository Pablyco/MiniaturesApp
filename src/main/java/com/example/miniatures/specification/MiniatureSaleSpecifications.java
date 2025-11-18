package com.example.miniatures.specification;


import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class MiniatureSaleSpecifications {


    public static Specification<MiniatureSale>hasType(MiniatureType type){
        return (root, query, cb) ->{
            if (type == null) return null;
            return cb.equal(root.get("type"), type);
        };
    }

    public static Specification<MiniatureSale> hasScale(MiniatureScale scale) {
        return (root, query, cb) -> {
            if (scale == null) return null;
            return cb.equal(root.get("scale"), scale);
        };
    }
    public static Specification<MiniatureSale> hasTypeAndScale(MiniatureType type,MiniatureScale scale) {
        return (root, query, cb) -> {
            if (type == null && scale == null) return null;
            if (type == null) return cb.equal(root.get("scale"),scale);
            if (scale == null) return cb.equal(root.get("type"), type);
            return cb.and(
                    cb.equal(root.get("type"), type),
                    cb.equal(root.get("scale"), scale)
            );
        };
    }

    public static Specification<MiniatureSale> clientNameContains(String clientName) {
        return (root, query, cb) -> {
            if (clientName == null || clientName.isBlank()) return null;
            return cb.like(cb.lower(root.get("clientName")), "%" + clientName.toLowerCase() + "%");
        };
    }

    public static Specification<MiniatureSale> priceGreaterThan(BigDecimal price) {
        return (root,query,cb) ->{
            if (price == null) return null;
            return cb.greaterThan(root.get("price"), price);
        };
    }

    public static Specification<MiniatureSale> priceLessThan(BigDecimal price) {
        return (root,query,cb) ->{
            if (price == null) return null;
            return cb.lessThan(root.get("price"), price);
        };
    }

    public static Specification<MiniatureSale> priceBetween(BigDecimal min, BigDecimal max) {
        return (root,query,cb) ->{
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("price"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.between(root.get("price"), min, max);
        };
    }

    public static Specification<MiniatureSale> saleDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            Path<LocalDate> datePath = root.get("saleDate");
            if (start == null) return cb.lessThanOrEqualTo(datePath, end);
            if (end == null) return cb.greaterThanOrEqualTo(datePath, start);
            return cb.between(datePath, start, end);
        };
    }






}
