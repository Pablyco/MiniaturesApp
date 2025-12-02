package com.example.miniatures.service.sales;

import com.example.miniatures.dto.miniatureSale.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleUpdateDTO;
import com.example.miniatures.dto.miniatureSale.SalesFilterDTO;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.repository.MiniatureSaleRepository;
import com.example.miniatures.service.MiniatureClientService;
import com.example.miniatures.service.MiniatureSaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        MiniatureSaleCreateDTO dto = new MiniatureSaleCreateDTO();
        dto.setName("Emperor of Mankind");
        dto.setPrice(new BigDecimal("15000"));
        dto.setSaleDate(LocalDate.now());
        dto.setType(MiniatureType.WARHAMMER);
        dto.setScale(MiniatureScale.LARGE_170MM);
        dto.setClientId(1L);

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
        savedSale.setClient(client); // <-- IMPORTANT: required by mapper.

        when(miniatureSaleRepository.save(any(MiniatureSale.class)))
                .thenReturn(savedSale);

        MiniatureSaleResponseDTO resultDto = miniatureSaleService.createMiniatureSale(dto);

        assertEquals("Emperor of Mankind", resultDto.getName());
        assertEquals(new BigDecimal("15000"), resultDto.getPrice());
        assertEquals(1L, resultDto.getClientId());
        assertEquals("Pablyco", resultDto.getClientName());
        assertEquals(MiniatureType.WARHAMMER, resultDto.getType());
        assertEquals(MiniatureScale.LARGE_170MM, resultDto.getScale());


        verify(miniatureClientService, times(1)).getClientEntityById(1L);
        verify(miniatureSaleRepository, times(1)).save(any(MiniatureSale.class));
    }

    @Test
    void getAllSales(){

        MiniatureClient client = new MiniatureClient();
        client.setId(999L);
        client.setName("Test Client");

        MiniatureSale sale1 = new MiniatureSale();
        sale1.setId(1L);
        sale1.setName("Emperor of Mankind");
        sale1.setPrice(new BigDecimal("15000"));
        sale1.setSaleDate(LocalDate.now());
        sale1.setType(MiniatureType.WARHAMMER);
        sale1.setScale(MiniatureScale.LARGE_170MM);
        sale1.setClient(client); // <-- IMPORTANT: required by mapper

        MiniatureSale sale2 = new MiniatureSale();
        sale2.setId(2L);
        sale2.setName("Horus Lupercal");
        sale2.setPrice(new BigDecimal("25000"));
        sale2.setSaleDate(LocalDate.now());
        sale2.setType(MiniatureType.WARHAMMER);
        sale2.setScale(MiniatureScale.SMALL_45MM);
        sale2.setClient(client);

        SalesFilterDTO filters = new SalesFilterDTO();

        when(miniatureSaleRepository.findAll(
                ArgumentMatchers.<Specification<MiniatureSale>>any()
        )).thenReturn(Arrays.asList(sale1, sale2));

        List<MiniatureSaleResponseDTO> dtos = miniatureSaleService.getSales(filters);

        assertEquals(2, dtos.size());
        assertEquals(new BigDecimal("15000"), dtos.get(0).getPrice());
        assertEquals(new BigDecimal("25000"), dtos.get(1).getPrice());
        assertEquals(MiniatureScale.SMALL_45MM, dtos.get(1).getScale());

        verify(miniatureSaleRepository, times(1))
                .findAll(ArgumentMatchers.<Specification<MiniatureSale>>any());
    }

    @Test
    void getAllSalesById(){

        MiniatureClient client = new MiniatureClient();
        client.setId(999L);
        client.setName("Test Client");

        MiniatureSale sale1 = new MiniatureSale();
        sale1.setId(1L);
        sale1.setName("Emperor of Mankind");
        sale1.setPrice(new BigDecimal("15000"));
        sale1.setSaleDate(LocalDate.now());
        sale1.setType(MiniatureType.WARHAMMER);
        sale1.setScale(MiniatureScale.LARGE_170MM);
        sale1.setClient(client);

        when(miniatureSaleRepository.findById(1L)).thenReturn(Optional.of(sale1));

        MiniatureSaleResponseDTO dto = miniatureSaleService.getSaleById(1L);

        assertEquals("Emperor of Mankind", dto.getName());
        assertEquals(new BigDecimal("15000"), dto.getPrice());
        assertEquals(MiniatureScale.LARGE_170MM, dto.getScale());

        verify(miniatureSaleRepository, times(1)).findById(1L);
    }

    @Test
    void getAllSalesByClient(){
        MiniatureClient client = new MiniatureClient();
        client.setId(1L);
        client.setName("Test Client");

        MiniatureSale sale1 = new MiniatureSale();
        sale1.setId(1L);
        sale1.setName("Emperor of Mankind");
        sale1.setPrice(new BigDecimal("15000"));
        sale1.setSaleDate(LocalDate.now());
        sale1.setType(MiniatureType.WARHAMMER);
        sale1.setScale(MiniatureScale.LARGE_170MM);
        sale1.setClient(client);

        MiniatureSale sale2 = new MiniatureSale();
        sale2.setId(2L);
        sale2.setName("Horus Lupercal");
        sale2.setPrice(new BigDecimal("25000"));
        sale2.setSaleDate(LocalDate.now());
        sale2.setType(MiniatureType.WARHAMMER);
        sale2.setScale(MiniatureScale.SMALL_45MM);
        sale2.setClient(client);

        when(miniatureSaleRepository.findByClientId(1L)).thenReturn(Arrays.asList(sale1, sale2));

        List<MiniatureSaleResponseDTO> dtos = miniatureSaleService.getSalesByClientId(1L);

        assertEquals(2, dtos.size());
        assertEquals(new BigDecimal("15000"), dtos.get(0).getPrice());
        assertEquals(new BigDecimal("25000"), dtos.get(1).getPrice());
        assertEquals(MiniatureScale.SMALL_45MM, dtos.get(1).getScale());
        assertEquals(MiniatureScale.LARGE_170MM, dtos.get(0).getScale());

        verify(miniatureSaleRepository, times(1)).findByClientId(1L);
    }


    @Test
    void deleteSaleSuccessfully(){
        MiniatureClient client = new MiniatureClient();
        client.setId(1L);
        client.setName("Test Client");

        MiniatureSale sale1 = new MiniatureSale();
        sale1.setId(1L);
        sale1.setName("Emperor of Mankind");
        sale1.setPrice(new BigDecimal("15000"));
        sale1.setSaleDate(LocalDate.now());
        sale1.setType(MiniatureType.WARHAMMER);
        sale1.setScale(MiniatureScale.LARGE_170MM);
        sale1.setClient(client);

        when(miniatureSaleRepository.findById(1L)).thenReturn(Optional.of(sale1));

        miniatureSaleService.deleteMiniatureSale(1L);

        verify(miniatureSaleRepository, times(1)).findById(1L);
        verify(miniatureSaleRepository, times(1)).delete(sale1);
    }

    @Test
    void deleteSaleNotFound(){
        when(miniatureSaleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> miniatureSaleService.deleteMiniatureSale(1L));

        verify(miniatureSaleRepository, times(1)).findById(1L);
        verify(miniatureSaleRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateSaleSuccessfully(){
        MiniatureClient client = new MiniatureClient();
        client.setId(1L);
        client.setName("Test Client");

        MiniatureSale sale1 = new MiniatureSale();
        sale1.setId(1L);
        sale1.setName("Emperor of Mankind");
        sale1.setPrice(new BigDecimal("15000"));
        sale1.setSaleDate(LocalDate.now());
        sale1.setType(MiniatureType.WARHAMMER);
        sale1.setScale(MiniatureScale.LARGE_170MM);
        sale1.setClient(client);

        MiniatureSale saleUpdated = new MiniatureSale();
        saleUpdated.setId(1L);
        saleUpdated.setName("Horus Lupercal");
        saleUpdated.setPrice(new BigDecimal("25000"));
        saleUpdated.setSaleDate(LocalDate.now());
        saleUpdated.setType(MiniatureType.WARHAMMER);
        saleUpdated.setScale(MiniatureScale.SMALL_45MM);
        saleUpdated.setClient(client);

        MiniatureSaleUpdateDTO dto = new MiniatureSaleUpdateDTO();
        dto.setName("Horus Lupercal");
        dto.setPrice(new BigDecimal("25000"));
        dto.setSaleDate(LocalDate.now());
        dto.setType(MiniatureType.WARHAMMER);
        dto.setScale(MiniatureScale.SMALL_45MM);


        when(miniatureSaleRepository.findById(1L)).thenReturn(Optional.of(sale1));
        when(miniatureSaleRepository.save(any(MiniatureSale.class))).thenReturn(saleUpdated);

        MiniatureSaleResponseDTO result = miniatureSaleService.updateMiniatureSale(dto,1L);

        assertEquals("Horus Lupercal",  result.getName());
        assertEquals(new BigDecimal("25000"), result.getPrice());


        verify(miniatureSaleRepository, times(1)).findById(1L);
        verify(miniatureSaleRepository, times(1)).save(any(MiniatureSale.class));
    }

    @Test
    void updateSaleNotFound(){

        MiniatureSaleUpdateDTO dto = new MiniatureSaleUpdateDTO();

        when(miniatureSaleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> miniatureSaleService.updateMiniatureSale(dto,1L) );

        verify(miniatureSaleRepository, times(1)).findById(1L);
        verify(miniatureSaleRepository, never()).save(any(MiniatureSale.class));
    }
}
