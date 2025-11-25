package com.example.miniatures.controller;

import com.example.miniatures.dto.miniatureClient.MiniatureClientCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientResponseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientUpdateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.service.MiniatureClientService;
import com.example.miniatures.service.MiniatureSaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MiniatureClientController {

    private final MiniatureClientService miniatureClientService;
    private final MiniatureSaleService miniatureSaleService;

    public MiniatureClientController(MiniatureClientService miniatureClientService,  MiniatureSaleService miniatureSaleService) {
        this.miniatureClientService = miniatureClientService;
        this.miniatureSaleService = miniatureSaleService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<MiniatureClientResponseDTO>> getMiniatureClients() {
        return ResponseEntity.ok(miniatureClientService.getClients());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<MiniatureClientResponseDTO> getClientById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureClientService.getClientById(id));
    }

    @GetMapping("clients/{id}/sales")
    public ResponseEntity<List<MiniatureSaleResponseDTO>> getClientSales(@PathVariable long id) {
        return ResponseEntity.ok(miniatureSaleService.getSalesByClientId(id));
    }

    @PostMapping("/clients")
    public ResponseEntity<MiniatureClientResponseDTO> createClient(@RequestBody MiniatureClientCreateDTO client) {
        MiniatureClientResponseDTO createdClient = miniatureClientService.createMiniatureClient(client);
        return ResponseEntity.status(202).body(createdClient);
    }
    @PutMapping("/clients/{id}")
    public ResponseEntity<MiniatureClientResponseDTO> updateClient(@PathVariable long id, @RequestBody MiniatureClientUpdateDTO client) {
        MiniatureClientResponseDTO updatedClient = miniatureClientService.updateMiniatureClient(client,id);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        miniatureClientService.deleteMiniatureClient(id);
        return ResponseEntity.noContent().build();
    }

}
