package com.example.miniatures.service;

import com.example.miniatures.exception.ResourceNotFoundException;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.repository.MiniatureClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniatureClientService {

    private final MiniatureClientRepository miniatureClientRepository;

    public MiniatureClientService(MiniatureClientRepository miniatureClientRepository) {
        this.miniatureClientRepository = miniatureClientRepository;
    }

    public List<MiniatureClient> getClients() {
        return miniatureClientRepository.findAll();
    }

    public MiniatureClient getClientById(long id) {
        return miniatureClientRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Client with ID " + id + " not found"));

    }

    public MiniatureClient createMiniatureClient(MiniatureClient miniatureClient) {
        return miniatureClientRepository.save(miniatureClient);
    }

    public MiniatureClient updateMiniatureClient(MiniatureClient client, long id) {
        return miniatureClientRepository.findById(id)
                .map(MiniatureClient->{

                    MiniatureClient.setName(client.getName());
                    MiniatureClient.setEmail(client.getEmail());
                    MiniatureClient.setPhoneNumber( client.getPhoneNumber());
                    MiniatureClient.setSales(client.getSales());
                    return  miniatureClientRepository.save(MiniatureClient);

        }).orElseThrow( ()-> new ResourceNotFoundException("Client with ID " + id + " not found"));
    }

    public void deleteMiniatureClient(long id) {
        MiniatureClient client = miniatureClientRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Client with ID " + id + " not found"));
        miniatureClientRepository.delete(client);
    }
}

