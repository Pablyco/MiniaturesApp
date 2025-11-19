package com.example.miniatures.controller;

import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.service.MiniatureClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MiniatureClientController {

    private final MiniatureClientService miniatureClientService;

    public MiniatureClientController(MiniatureClientService miniatureClientService) {
        this.miniatureClientService = miniatureClientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<MiniatureClient>> getMiniatureClients() {
        return ResponseEntity.ok(miniatureClientService.getClients());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<MiniatureClient> getClientById(@PathVariable long id) {
        return ResponseEntity.ok(miniatureClientService.getClientById(id));
    }

    @PostMapping("/clients")
    public ResponseEntity<MiniatureClient> createClient(@RequestBody MiniatureClient client) {
        MiniatureClient createdClient = miniatureClientService.createMiniatureClient(client);
        return ResponseEntity.status(202).body(createdClient);
    }
    @PutMapping("/clients/{id}")
    public ResponseEntity<MiniatureClient> updateClient(@PathVariable long id, @RequestBody MiniatureClient client) {
        MiniatureClient updatedClient = miniatureClientService.updateMiniatureClient(client,id);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        miniatureClientService.deleteMiniatureClient(id);
        return ResponseEntity.noContent().build();
    }

}
