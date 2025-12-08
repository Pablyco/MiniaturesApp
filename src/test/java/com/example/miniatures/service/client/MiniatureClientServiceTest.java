package com.example.miniatures.service.client;

import com.example.miniatures.dto.miniatureClient.MiniatureClientCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureClientResponseDTO;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.repository.MiniatureClientRepository;
import com.example.miniatures.service.MiniatureClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class MiniatureClientServiceTest {

    @Mock
    MiniatureClientRepository repository;

    @InjectMocks
    MiniatureClientService service;

    @Test
    void getClient_returnsMiniatureClientDto()
    {
        MiniatureClient miniatureClient = new MiniatureClient();
        miniatureClient.setId(1L);
        miniatureClient.setName("John");
        miniatureClient.setEmail("john@mail.com");
        miniatureClient.setPhoneNumber(12345);

        MiniatureClient miniatureClient2 = new MiniatureClient();
        miniatureClient2.setId(1L);
        miniatureClient2.setName("Peter");
        miniatureClient2.setEmail("peter@mail.com");
        miniatureClient2.setPhoneNumber(12121);

        when(repository.findAll()).thenReturn(Arrays.asList(miniatureClient,miniatureClient2));

        List<MiniatureClientResponseDTO> clients = service.getClients();

        assertEquals(2, clients.size());
        assertEquals("John", clients.get(0).getName());
        assertEquals("Peter", clients.get(1).getName());
        assertEquals("john@mail.com", clients.get(0).getEmail());

        verify(repository,times(1)).findAll();
    }

    @Test
    void getClientById(){
        MiniatureClient miniatureClient = new MiniatureClient();
        miniatureClient.setId(1L);
        miniatureClient.setName("John");
        miniatureClient.setEmail("john@mail.com");
        miniatureClient.setPhoneNumber(12345);

        when(repository.findById(1L)).thenReturn(Optional.of(miniatureClient));

        MiniatureClientResponseDTO clientDto = service.getClientById(1L);

        assertEquals("John", clientDto.getName());
        assertEquals("john@mail.com", clientDto.getEmail());
        assertEquals(12345, clientDto.getPhoneNumber());

        verify(repository,times(1)).findById(1L);
    }
    @Test
    void getClientByIdNotFound(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getClientById(1L));

        verify(repository,times(1)).findById(1L);
    }

    @Test
    void createClient_withValidData() {
        MiniatureClientCreateDTO dto = new MiniatureClientCreateDTO();
        dto.setName("John");
        dto.setEmail("john@mail");
        dto.setPhoneNumber(12345);

        MiniatureClient savedClient = new MiniatureClient();
        savedClient.setId(1L);
        savedClient.setName(dto.getName());
        savedClient.setEmail(dto.getEmail());
        savedClient.setPhoneNumber(dto.getPhoneNumber());

        when(repository.save(any(MiniatureClient.class))).thenReturn(savedClient);

        MiniatureClientResponseDTO resultDto = service.createMiniatureClient(dto);

        assertEquals("John", resultDto.getName());
        assertEquals("john@mail",  resultDto.getEmail());
        assertEquals(12345, resultDto.getPhoneNumber());

        verify(repository,times(1)).save(any(MiniatureClient.class));
    }

    @Test
    void deleteClient_withValidData() {
        MiniatureClient miniatureClient = new MiniatureClient();
        miniatureClient.setId(1L);
        miniatureClient.setName("John");
        miniatureClient.setEmail("john@mail.com");
        miniatureClient.setPhoneNumber(12345);

        when(repository.findById(1L)).thenReturn(Optional.of(miniatureClient));

        service.deleteMiniatureClient(1L);

        verify(repository,times(1)).findById(1L);
        verify(repository,times(1)).delete(miniatureClient);
    }

    @Test
    void deleteClient_withNotFound(){

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deleteMiniatureClient(1L));

        verify(repository,times(1)).findById(1L);
        verify(repository,never()).delete(any());
    }
}
