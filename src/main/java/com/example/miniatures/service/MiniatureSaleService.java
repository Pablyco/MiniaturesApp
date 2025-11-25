package com.example.miniatures.service;

import com.example.miniatures.dto.miniatureSale.*;
import com.example.miniatures.exception.ResourceNotFoundException;
import com.example.miniatures.model.MiniatureClient;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.repository.MiniatureSaleRepository;
import com.example.miniatures.specification.MiniatureSaleSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<MiniatureSaleResponseDTO> getSales(SalesFilterDTO filters){
        /*
         * Specification<MiniatureSale> spec = Specification.where(null); Deprecated
         *
         * New updated method with .unrestricted();
         */
        Specification<MiniatureSale> spec = Specification.unrestricted();


        if(filters.getType() != null ||  filters.getScale() != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasTypeAndScale(filters.getType(), filters.getScale()));
        }

        if(filters.getClientId() != null) {
            spec = spec.and(MiniatureSaleSpecifications.hasClientId(filters.getClientId()));
        }

        if(filters.getMinPrice() != null || filters.getMaxPrice() != null){
            spec = spec.and(MiniatureSaleSpecifications.priceBetween(filters.getMinPrice(), filters.getMaxPrice()));
        }

        if (filters.getStartDate() != null || filters.getEndDate() != null) {
            spec = spec.and(MiniatureSaleSpecifications.saleDateBetween(filters.getStartDate(), filters.getEndDate()));
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
