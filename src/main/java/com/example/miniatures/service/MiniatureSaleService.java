package com.example.miniatures.service;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.repository.MiniatureSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MiniatureSaleService {

    private final MiniatureSaleRepository miniatureSaleRepository;

    public MiniatureSaleService(MiniatureSaleRepository miniatureSaleRepository) {
        this.miniatureSaleRepository = miniatureSaleRepository;
    }

    public List<MiniatureSale> getSales() {
        return miniatureSaleRepository.findAll();
    }

    public MiniatureSale createMiniatureSale(MiniatureSale miniatureSale) {
        return miniatureSaleRepository.save(miniatureSale);
    }
}
