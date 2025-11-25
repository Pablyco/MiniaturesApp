package com.example.miniatures.controller;

import com.example.miniatures.dto.miniatureSale.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleUpdateDTO;
import com.example.miniatures.dto.miniatureSale.SalesFilterDTO;
import com.example.miniatures.service.MiniatureSaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MiniatureSaleController {

    private final MiniatureSaleService miniatureSaleService;

    public MiniatureSaleController(MiniatureSaleService miniatureSaleService) {
        this.miniatureSaleService = miniatureSaleService;
    }

    // *** Miniature sales find methods ***

    /*
     * Main Method to help you to obtain sales saved in database.
     */
    @GetMapping("/sales")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getSales(SalesFilterDTO filters) {
        return ResponseEntity.ok(miniatureSaleService.getSales(filters));
    }

    /*
     * Gets a specific sale by its id.
     */
    @GetMapping("/sales/{id}")
    public ResponseEntity<MiniatureSaleResponseDTO> getSaleById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureSaleService.getSaleById(id));
    }

    /*
     * Gets the last 10 sales
     */
    @GetMapping("/sales/latest")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getLastSales(){
        return ResponseEntity.ok(miniatureSaleService.getLastSales());
    }

    /*
     * Creates a sale.
     */
    @PostMapping("/sales")
    public ResponseEntity<MiniatureSaleResponseDTO> createSale(@Valid @RequestBody MiniatureSaleCreateDTO dto) {
        return ResponseEntity.status(201).body(miniatureSaleService.createMiniatureSale(dto));
    }

    /*
     * Update a specific sale by id.
     */
    @PutMapping("/sales/{id}")
    public ResponseEntity<MiniatureSaleResponseDTO> updateSaleById(@Valid @RequestBody MiniatureSaleUpdateDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(miniatureSaleService.updateMiniatureSale(dto,id));
    }

    /*
     * Delete a specific sale by id.
     */
    @DeleteMapping("sales/{id}")
    public ResponseEntity<Void> deleteSaleByID(@PathVariable Long id) {
        miniatureSaleService.deleteMiniatureSale(id);
        return ResponseEntity.noContent().build();
    }

}
