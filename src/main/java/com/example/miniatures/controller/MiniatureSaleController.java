package com.example.miniatures.controller;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.service.MiniatureSaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MiniatureSaleController {

    private final MiniatureSaleService miniatureSaleService;

    public MiniatureSaleController(MiniatureSaleService miniatureSaleService) {
        this.miniatureSaleService = miniatureSaleService;
    }

    /*
     * Method to help you to obtain all sales saved in database.
     */
    @GetMapping("/Sales")
    public ResponseEntity<List<MiniatureSale>> getSales() {
        List<MiniatureSale> sales = miniatureSaleService.getSales();
        return ResponseEntity.ok(sales);
    }

    /*
     * Creates a sale.
     */
    @PostMapping("/CreateSale")
    public ResponseEntity<MiniatureSale> createSale(@RequestBody MiniatureSale miniatureSale) {
         MiniatureSale saleCreated = miniatureSaleService.createMiniatureSale(miniatureSale);
        return ResponseEntity.status(201).body(saleCreated);
    }

    /*
     * Update a specific sale by id.
     */
    @PutMapping("/Update/{id}")
    public ResponseEntity<MiniatureSale> updateSaleById(@RequestBody MiniatureSale miniatureSale, @PathVariable Long id) {
        MiniatureSale updatedSale = miniatureSaleService.updateMiniatureSale(miniatureSale,id);
        return ResponseEntity.ok(updatedSale);
    }

    /*
     * Delete a specific sale by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleByID(@PathVariable Long id) {
        miniatureSaleService.deleteMiniatureSale(id);
        return ResponseEntity.noContent().build();
    }

}
