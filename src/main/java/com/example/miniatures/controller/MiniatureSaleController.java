package com.example.miniatures.controller;

import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.service.MiniatureSaleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MiniatureSale> getSales() {
        return miniatureSaleService.getSales();
    }

    /*
     * Creates a sale.
     */

    @PostMapping("/CreateSale")
    public MiniatureSale createSale(@RequestBody MiniatureSale miniatureSale) {
        return miniatureSaleService.createMiniatureSale(miniatureSale);
    }

    /*
     * Update a specific sale by id.
     */
    @PutMapping("/Update/{id}")
    public ResponseEntity<MiniatureSale> updateSaleById(@RequestBody MiniatureSale miniatureSale, @PathVariable Long id) {
        return miniatureSaleService.updateMiniatureSale(miniatureSale,id);
    }
    /*
     * Delete a specific sale by id.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleByID(@PathVariable Long id) {
        miniatureSaleService.deleteMiniatureSale(id);
        return ResponseEntity.ok().build();
    }


}
