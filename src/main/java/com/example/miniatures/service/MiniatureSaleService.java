package com.example.miniatures.service;

import com.example.miniatures.dto.miniatureClient.MiniatureSaleBaseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureSaleCreateDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureSaleResponseDTO;
import com.example.miniatures.dto.miniatureClient.MiniatureSaleUpdateDTO;
import com.example.miniatures.exception.ResourceNotFoundException;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.repository.MiniatureSaleRepository;
import com.example.miniatures.specification.MiniatureSaleSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MiniatureSaleService {

    private final MiniatureSaleRepository miniatureSaleRepository;
    private final MiniatureClientService miniatureClientService;

    public MiniatureSaleService(MiniatureSaleRepository miniatureSaleRepository,  MiniatureClientService miniatureClientService) {
        this.miniatureSaleRepository = miniatureSaleRepository;
        this.miniatureClientService = miniatureClientService;
    }


     // *** Miniature sales find/get methods ***

    /*
     * Main Method to help you to obtain sales saved in database.
     */
    public List<MiniatureSaleResponseDTO> getSales(
            MiniatureType type,
            MiniatureScale scale,
            Long clientId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            BigDecimal priceGt,
            BigDecimal priceLt,
            LocalDate startDate,
            LocalDate endDate
    ){
        /*
         * Specification<MiniatureSale> spec = Specification.where(null); Deprecated
         *
         * New updated method with .unrestricted();
         */
        Specification<MiniatureSale> spec = Specification.unrestricted();

        if(type != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasType(type));
        }

        if(scale != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasScale(scale));
        }

        if(type != null ||  scale != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasTypeAndScale(type, scale));
        }

        if(clientId != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasClientId(clientId));
        }

        if(minPrice != null || maxPrice != null){
            spec = spec.and(MiniatureSaleSpecifications.priceBetween(minPrice,maxPrice));
        }

        if(priceGt != null){
            spec = spec.and(MiniatureSaleSpecifications.priceGreaterThan(priceGt));
        }

        if(priceLt != null){
            spec = spec.and(MiniatureSaleSpecifications.priceLessThan(priceLt));
        }

        if (startDate != null || endDate != null) {
            spec = spec.and(MiniatureSaleSpecifications.saleDateBetween(startDate,endDate));
        }

        List<MiniatureSale> sales = miniatureSaleRepository.findAll(spec);
        return toResponseDTOList(sales);

    }

    /*
     * Gets a specific sale by its id.
     */
    public MiniatureSaleResponseDTO getSaleById(long id) {
        MiniatureSale sale = miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature Sale with ID " + id + " not found"));
        return toResponseDTO(sale);
    }

    /*
     * Gets the last 10 sales
     */
    public List<MiniatureSaleResponseDTO> getLastSales(){
        List<MiniatureSale> sales = miniatureSaleRepository.findTop10ByOrderBySaleDateDesc();
        throwExceptionIfEmpty(sales, "Sales not found");
        return toResponseDTOList(sales);
    }

    /*
     * Create a new Miniature.
     */
    public MiniatureSaleResponseDTO createMiniatureSale(MiniatureSaleCreateDTO dto) {
        MiniatureClient client = miniatureClientService.getClientById(dto.getClientId());
        MiniatureSale sale = new MiniatureSale();

        mapDtoToSale(dto,sale,client);

        MiniatureSale savedSale = miniatureSaleRepository.save(sale);
        return toResponseDTO(savedSale);
    }

    public MiniatureSaleResponseDTO updateMiniatureSale(MiniatureSaleUpdateDTO dto, Long id) {

        MiniatureSale foundSale = miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature sale with ID " + id + " not found"));

        MiniatureClient client = miniatureClientService.getClientById(dto.getClientId());

        mapDtoToSale(dto,foundSale,client);

        MiniatureSale savedSale = miniatureSaleRepository.save(foundSale);
        return toResponseDTO(savedSale);
    }

    public void deleteMiniatureSale(Long id) {
        MiniatureSale sale = miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature sale with ID " + id + " not found"));
        miniatureSaleRepository.delete(sale);
    }

    private static void throwExceptionIfEmpty(List<MiniatureSale> sales, String message) {
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(message);
        }
    }

    private static MiniatureSaleResponseDTO toResponseDTO(MiniatureSale sale){
        MiniatureSaleResponseDTO dto = new MiniatureSaleResponseDTO();
        dto.setId(sale.getId());
        dto.setName(sale.getName());
        dto.setPrice(sale.getPrice());
        dto.setSaleDate(sale.getSaleDate());
        dto.setType(sale.getType());
        dto.setScale(sale.getScale());

        dto.setClientId(sale.getClient().getId());
        dto.setClientName(sale.getClient().getName());
        return dto;
    }

    private static List<MiniatureSaleResponseDTO> toResponseDTOList(List<MiniatureSale> sales){
        return sales.stream().map(MiniatureSaleService::toResponseDTO).toList();
    }

    private void mapDtoToSale(MiniatureSaleBaseDTO dto, MiniatureSale sale, MiniatureClient client){
        sale.setClient(client);
        sale.setType(dto.getType());
        sale.setScale(dto.getScale());
        sale.setPrice(dto.getPrice());
        sale.setSaleDate(dto.getSaleDate());
        sale.setName(dto.getName());
    }
}
