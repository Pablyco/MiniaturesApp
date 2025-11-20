package com.example.miniatures.controller;

import com.example.miniatures.dto.miniatureClient.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureSaleResponseDTO;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.service.MiniatureSaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ResponseEntity<List<MiniatureSale>> getSales(
            @RequestParam(required = false) MiniatureType type,
            @RequestParam(required = false) MiniatureScale scale,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal priceGt,
            @RequestParam(required = false) BigDecimal priceLt,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate

    ) {
        var sales = miniatureSaleService.getSales(
                type,scale,clientId,
                minPrice,maxPrice,priceGt,
                priceLt,startDate, endDate
        );
        return ResponseEntity.ok(sales);

    }

    /*
     * Gets a specific sale by its id.
     */
    @GetMapping("/sales/{id}")
    public ResponseEntity<MiniatureSale> getSaleById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureSaleService.getSaleById(id));
    }

    /*
     * Gets the last sale of a client.
     */
    @GetMapping("/sales/client/{clientName}/last")
    public ResponseEntity<MiniatureSale> getLastSaleByClientName(@RequestParam String clientName) {
        return ResponseEntity.ok(miniatureSaleService.getLastSaleByClientName(clientName));
    }

    /*
     * Gets the last 10 sales
     */
    @GetMapping("/sales/latest")
    public ResponseEntity<List<MiniatureSale>> getLastSales(){
        List<MiniatureSale> sales = miniatureSaleService.getLastSales();
        return ResponseEntity.ok(sales);
    }

    /*
     * Creates a sale.
     */
    @PostMapping("/sales")
    public ResponseEntity<MiniatureSaleResponseDTO> createSale(@Valid @RequestBody MiniatureSaleCreateDTO saleCreateDTO) {
        MiniatureSaleResponseDTO saleCreated = miniatureSaleService.createMiniatureSale(saleCreateDTO);
        return ResponseEntity.status(201).body(saleCreated);
    }

    /*
     * Update a specific sale by id.
     */
    @PutMapping("/sales/{id}")
    public ResponseEntity<MiniatureSale> updateSaleById(@RequestBody MiniatureSale miniatureSale, @PathVariable Long id) {
        MiniatureSale updatedSale = miniatureSaleService.updateMiniatureSale(miniatureSale,id);
        return ResponseEntity.ok(updatedSale);
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
