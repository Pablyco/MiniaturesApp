package com.example.miniatures.controller;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.service.MiniatureSaleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MiniatureSaleController {

    private MiniatureSaleService miniatureSaleService;

    @GetMapping("/Sales")
    public List<MiniatureSale> getSales() {
        return miniatureSaleService.getSales();
    }

    @PostMapping("/CreateSale")
    public MiniatureSale createSale(@RequestBody MiniatureSale miniatureSale) {
        return null; //funcion de service
    }


}
