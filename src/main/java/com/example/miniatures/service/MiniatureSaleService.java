package com.example.miniatures.service;

import com.example.miniatures.exception.ResourceNotFoundException;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.repository.MiniatureSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MiniatureSaleService {

    private final MiniatureSaleRepository miniatureSaleRepository;

    public MiniatureSaleService(MiniatureSaleRepository miniatureSaleRepository) {
        this.miniatureSaleRepository = miniatureSaleRepository;
    }

    /*
    *   Find methods
     */
    public List<MiniatureSale> getSales() {
        return miniatureSaleRepository.findAll();
    }

    public MiniatureSale getSaleById(long id) {
        return miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature with ID " + id + " not found"));
    }

    public List<MiniatureSale> getSalesByType(MiniatureType type) {
        List<MiniatureSale> sales = miniatureSaleRepository.findByType(type);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("Sales with miniature type " + type + " not found");
        }
        return sales;
    }

    public MiniatureSale createMiniatureSale(MiniatureSale miniatureSale) {
        return miniatureSaleRepository.save(miniatureSale);
    }

    public Optional<MiniatureSale> getMiniatureSaleById(Long id) {
        return miniatureSaleRepository.findById(id);
    }

    public MiniatureSale updateMiniatureSale(MiniatureSale miniatureSale, Long id) {
        return miniatureSaleRepository.findById(id)
                .map(MiniatureSale->{
                    MiniatureSale.setName(miniatureSale.getName());
                    MiniatureSale.setPrice(miniatureSale.getPrice());
                    MiniatureSale.setSaleDate(LocalDate.now());
                    MiniatureSale.setType(miniatureSale.getType());
                    MiniatureSale.setScale(miniatureSale.getScale());
                    return miniatureSaleRepository.save(MiniatureSale);
                })
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature with ID " + id + " not found"));
    }

    public void deleteMiniatureSale(Long id) {
        MiniatureSale sale = miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature with ID " + id + " not found"));
        miniatureSaleRepository.delete(sale);
    }

}
