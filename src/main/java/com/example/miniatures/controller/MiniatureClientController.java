package com.example.miniatures.controller;

import com.example.miniatures.dto.miniatureClient.MiniatureClientCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientResponseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientUpdateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.service.MiniatureClientService;
import com.example.miniatures.service.MiniatureSaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
public class MiniatureClientController {

    private final MiniatureClientService miniatureClientService;
    private final MiniatureSaleService miniatureSaleService;

    public MiniatureClientController(MiniatureClientService miniatureClientService,  MiniatureSaleService miniatureSaleService) {
        this.miniatureClientService = miniatureClientService;
        this.miniatureSaleService = miniatureSaleService;
    }

    @Operation(summary = "Get clients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clients successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not clients found"),
    })
    @GetMapping("/clients")
    public ResponseEntity<List<MiniatureClientResponseDTO>> getMiniatureClients() {
        return ResponseEntity.ok(miniatureClientService.getClients());
    }

    @Operation(summary = "Get sale by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "client found"),
            @ApiResponse(responseCode = "404", description = "client not found")
    })
    @GetMapping("/clients/{id}")
    public ResponseEntity<MiniatureClientResponseDTO> getClientById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureClientService.getClientById(id));
    }

    @Operation(summary = "Get a list of all client sales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sales found"),
            @ApiResponse(responseCode = "404", description = "client not found")
    })
    @GetMapping("clients/{id}/sales")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getClientSales(@PathVariable long id) {
        return ResponseEntity.ok(miniatureSaleService.getSalesByClientId(id));
    }

    @Operation(
            summary = "Create a new client",
            description = "Creates a client and returns a DTO as response"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/clients")
    public ResponseEntity<MiniatureClientResponseDTO> createClient(@RequestBody MiniatureClientCreateDTO client) {
        MiniatureClientResponseDTO createdClient = miniatureClientService.createMiniatureClient(client);
        return ResponseEntity.status(202).body(createdClient);
    }

    @Operation(summary = "Update a client by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/clients/{id}")
    public ResponseEntity<MiniatureClientResponseDTO> updateClient(@PathVariable long id, @RequestBody MiniatureClientUpdateDTO client) {
        MiniatureClientResponseDTO updatedClient = miniatureClientService.updateMiniatureClient(client,id);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Delete a specific client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "The client was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        miniatureClientService.deleteMiniatureClient(id);
        return ResponseEntity.noContent().build();
    }

}
