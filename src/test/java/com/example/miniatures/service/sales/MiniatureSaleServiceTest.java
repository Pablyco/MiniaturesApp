package com.example.miniatures.service.sales;

import com.example.miniatures.dto.miniatureSale.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.repository.MiniatureSaleRepository;
import com.example.miniatures.service.MiniatureClientService;
import com.example.miniatures.service.MiniatureSaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MiniatureSaleServiceTest {

    @Mock
    MiniatureSaleRepository miniatureSaleRepository;

    @Mock
    MiniatureClientService miniatureClientService;

    @InjectMocks
    MiniatureSaleService miniatureSaleService;


    @Test
    void createMiniatureSale_withValidData_returnsResponseDto(){

        //Arrange
        //Fake DTO
        MiniatureSaleCreateDTO dto = new MiniatureSaleCreateDTO();
        dto.setName("Emperor of Mankind");
        dto.setPrice(new BigDecimal("15000"));
        dto.setSaleDate(LocalDate.now());
        dto.setType(MiniatureType.WARHAMMER);
        dto.setScale(MiniatureScale.LARGE_170MM);
        dto.setClientId(1L);

        //Fake Client
        MiniatureClient client = new MiniatureClient();
        client.setId(1L);
        client.setName("Pablyco");
        client.setPhoneNumber(666);
        client.setEmail("pablyco@mail.com");

        when(miniatureClientService.getClientEntityById(1L))
                .thenReturn(client);


        MiniatureSale savedSale = new MiniatureSale();
        savedSale.setId(10L);
        savedSale.setName(dto.getName());
        savedSale.setPrice(dto.getPrice());
        savedSale.setSaleDate(dto.getSaleDate());
        savedSale.setType(dto.getType());
        savedSale.setScale(dto.getScale());
        savedSale.setClient(client);

        when(miniatureSaleRepository.save(any(MiniatureSale.class)))
                .thenReturn(savedSale);

        //Act
        MiniatureSaleResponseDTO resultDto = miniatureSaleService.createMiniatureSale(dto);

        //Verify
        assertEquals("Emperor of Mankind", resultDto.getName());
        assertEquals(new BigDecimal("15000"), resultDto.getPrice());
        assertEquals(1L, resultDto.getClientId());
        assertEquals("Pablyco", resultDto.getClientName());
        assertEquals(MiniatureType.WARHAMMER, resultDto.getType());
        assertEquals(MiniatureScale.LARGE_170MM, resultDto.getScale());

        verify(miniatureClientService, times(1)).getClientEntityById(1L);
        verify(miniatureSaleRepository, times(1)).save(any(MiniatureSale.class));

    }
}
