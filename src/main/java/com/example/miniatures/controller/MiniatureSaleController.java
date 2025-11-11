package com.example.miniatures.controller;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.service.MiniatureSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MiniatureSaleController {

    private final MiniatureSaleService miniatureSaleService;

    public MiniatureSaleController(MiniatureSaleService miniatureSaleService) {
        this.miniatureSaleService = miniatureSaleService;
    }

    @GetMapping("/Sales")
    public List<MiniatureSale> getSales() {
        return miniatureSaleService.getSales();
    }

    @PostMapping("/CreateSale")
    public MiniatureSale createSale(@RequestBody MiniatureSale miniatureSale) {
        return miniatureSaleService.createMiniatureSale(miniatureSale); //funcion de service
    }


}
