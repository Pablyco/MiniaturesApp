package com.example.miniatures.controller;

import com.example.miniatures.dto.miniatureSale.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleUpdateDTO;
import com.example.miniatures.dto.miniatureSale.SalesFilterDTO;
import com.example.miniatures.service.MiniatureSaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
public class MiniatureSaleController {

    private final MiniatureSaleService miniatureSaleService;

    public MiniatureSaleController(MiniatureSaleService miniatureSaleService) {
        this.miniatureSaleService = miniatureSaleService;
    }


    @Operation(
            summary = "Get miniature sales",
            description = "Returns a list of miniature sales using optional filters such as type, scale, client ID, price ranges, and date ranges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sales successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid filter values"),
    })
    @GetMapping("/sales")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getSales(SalesFilterDTO filters) {
        return ResponseEntity.ok(miniatureSaleService.getSales(filters));
    }

    @Operation(summary = "Get sale by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @GetMapping("/sales/{id}")
    public ResponseEntity<MiniatureSaleResponseDTO> getSaleById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureSaleService.getSaleById(id));
    }

    @Operation(summary = "Get a list of the lastest 10 sales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @GetMapping("/sales/latest")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getLastSales(){
        return ResponseEntity.ok(miniatureSaleService.getLastSales());
    }

    @Operation(
            summary = "Create a new miniature sale",
            description = "Creates a miniature sale and returns a DTO as response"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sale successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PostMapping("/sales")
    public ResponseEntity<MiniatureSaleResponseDTO> createSale(@Valid @RequestBody MiniatureSaleCreateDTO dto) {
        return ResponseEntity.status(201).body(miniatureSaleService.createMiniatureSale(dto));
    }

    @Operation(summary = "Update a sale by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @PutMapping("/sales/{id}")
    public ResponseEntity<MiniatureSaleResponseDTO> updateSaleById(@Valid @RequestBody MiniatureSaleUpdateDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(miniatureSaleService.updateMiniatureSale(dto,id));
    }

    @Operation(summary = "Delete a specific sale")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "The Sale was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @DeleteMapping("sales/{id}")
    public ResponseEntity<Void> deleteSaleByID(@PathVariable Long id) {
        miniatureSaleService.deleteMiniatureSale(id);
        return ResponseEntity.noContent().build();
    }
}
