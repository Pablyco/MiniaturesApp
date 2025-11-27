package com.example.miniatures.service.sales;

import com.example.miniatures.dto.miniatureSale.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureSale.MiniatureSaleResponseDTO;
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

        //Arrange.
        //Fake DTO.
        MiniatureSaleCreateDTO dto = new MiniatureSaleCreateDTO();
        dto.setName("Emperor of Mankind");
        dto.setPrice(new BigDecimal("15000"));
        dto.setSaleDate(LocalDate.now());
        dto.setType(MiniatureType.WARHAMMER);
        dto.setScale(MiniatureScale.LARGE_170MM);
        dto.setClientId(1L);

        /*
         * Fake client.
         */
        MiniatureClient client = new MiniatureClient();
        client.setId(1L);
        client.setName("Pablyco");
        client.setPhoneNumber(666);
        client.setEmail("pablyco@mail.com");

        //When getClientEntityById "1" take the fake client
        when(miniatureClientService.getClientEntityById(1L))
                .thenReturn(client);

        /*
         * Fake saved sale.
         */
        MiniatureSale savedSale = new MiniatureSale();
        savedSale.setId(10L);
        savedSale.setName(dto.getName());
        savedSale.setPrice(dto.getPrice());
        savedSale.setSaleDate(dto.getSaleDate());
        savedSale.setType(dto.getType());
        savedSale.setScale(dto.getScale());
        savedSale.setClient(client); // <-- IMPORTANT: required by mapper.

        //When save a miniature sale take the fake one.
        when(miniatureSaleRepository.save(any(MiniatureSale.class)))
                .thenReturn(savedSale);

        //Act.
        MiniatureSaleResponseDTO resultDto = miniatureSaleService.createMiniatureSale(dto);

        //Verify.

        // The result dto has the expected name.
        assertEquals("Emperor of Mankind", resultDto.getName());

        // The result dto has the expected price.
        assertEquals(new BigDecimal("15000"), resultDto.getPrice());

        // The result dto has the expected client id.
        assertEquals(1L, resultDto.getClientId());

        // The result dto has the expected client name (me :D )
        assertEquals("Pablyco", resultDto.getClientName());

        // The result dto has the expected miniature type
        assertEquals(MiniatureType.WARHAMMER, resultDto.getType());

        // The result dto has the expected scale
        assertEquals(MiniatureScale.LARGE_170MM, resultDto.getScale());


         // If the client service NEVER called getClientEntityById(...) or if the number of invocations is greater than 1, the test fails.
        verify(miniatureClientService, times(1)).getClientEntityById(1L);

        // If the sale repository NEVER called save method(...) or if the number of invocations is greater than 1, the test fails. It should save once.
        verify(miniatureSaleRepository, times(1)).save(any(MiniatureSale.class));

    }

    @Test
    void getAllSales(){
        //Arrange.

        /*
         * Fake client that our sales will use.
         * The mapper requires sale.getClient().getId() and getName(),
         * so the client MUST be present, or the test will crash.
         */

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

        /*
         * These filters are empty, so the service will return ALL sales.
         */
        SalesFilterDTO filters = new SalesFilterDTO();

        /*
         * Our service calls repository.findAll(spec),
         * so we need to mock findAll(Specification).
         *
         * any(Specification.class) tells Mockito:
         * "No matter what spec you pass, return this list."
         */
        when(miniatureSaleRepository.findAll(
                ArgumentMatchers.<Specification<MiniatureSale>>any()
        )).thenReturn(Arrays.asList(sale1, sale2));

        //Act

        List<MiniatureSaleResponseDTO> dtos = miniatureSaleService.getSales(filters);

        //Verify

        // We got 2 sales
        assertEquals(2, dtos.size());

        // The first sale has the expected price.
        assertEquals(new BigDecimal("15000"), dtos.get(0).getPrice());

        // The second sale has the expected price.
        assertEquals(new BigDecimal("25000"), dtos.get(1).getPrice());

        // The second sale scale matches.
        assertEquals(MiniatureScale.SMALL_45MM, dtos.get(1).getScale());

        /*
         * This checks the interaction with the mock.
         * If the service NEVER called findAll(spec), the test fails.
         */
        verify(miniatureSaleRepository, times(1))
                .findAll(ArgumentMatchers.<Specification<MiniatureSale>>any());

    }
}
