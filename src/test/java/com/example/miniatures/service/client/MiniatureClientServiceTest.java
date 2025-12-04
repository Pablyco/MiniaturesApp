package com.example.miniatures.service.client;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
