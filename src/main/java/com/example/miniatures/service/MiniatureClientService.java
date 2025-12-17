package com.example.miniatures.service;

import com.example.miniatures.dto.miniatureClient.MiniatureClientBaseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientResponseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientUpdateDTO;
import com.example.miniatures.exception.ClientNotFoundException;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.repository.MiniatureClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniatureClientService {

    private final MiniatureClientRepository miniatureClientRepository;

    public MiniatureClientService(MiniatureClientRepository miniatureClientRepository) {
        this.miniatureClientRepository = miniatureClientRepository;
    }

    public List<MiniatureClientResponseDTO> getClients() {
        return toResponseDTOList(miniatureClientRepository.findAll());
    }

    public MiniatureClientResponseDTO getClientById(long id) {
        MiniatureClient client = miniatureClientRepository.findById(id)
                .orElseThrow( ()-> new ClientNotFoundException("Client with ID " + id + " not found"));
        return toResponseDTO(client);

    }

    public MiniatureClientResponseDTO createMiniatureClient(MiniatureClientCreateDTO dto) {
        MiniatureClient client = new MiniatureClient();
        mapDtoToClient(dto, client);
        MiniatureClient savedClient = miniatureClientRepository.save(client);
        return toResponseDTO(savedClient);
    }

    public MiniatureClientResponseDTO updateMiniatureClient(MiniatureClientUpdateDTO dto, long id) {
        MiniatureClient client = miniatureClientRepository.findById(id)
                .orElseThrow( ()-> new ClientNotFoundException("Client with ID " + id + " not found"));
        mapDtoToClient(dto, client);
        MiniatureClient updatedClient = miniatureClientRepository.save(client);
        return toResponseDTO(updatedClient);
    }

    public void deleteMiniatureClient(long id) {
        MiniatureClient client = miniatureClientRepository.findById(id)
                .orElseThrow( ()-> new ClientNotFoundException("Client with ID " + id + " not found"));
        miniatureClientRepository.delete(client);
    }

    private static MiniatureClientResponseDTO toResponseDTO(MiniatureClient client){
        MiniatureClientResponseDTO dto = new MiniatureClientResponseDTO();
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        return dto;
    }

    private static List<MiniatureClientResponseDTO> toResponseDTOList(List<MiniatureClient> clients){
        return clients.stream().map(MiniatureClientService::toResponseDTO).toList();
    }

    private void mapDtoToClient(MiniatureClientBaseDTO dto, MiniatureClient client){
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
    }

    public MiniatureClient getClientEntityById(Long id) {
        return miniatureClientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found"));
    }


}

