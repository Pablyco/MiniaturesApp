package com.example.miniatures.service;

import com.example.miniatures.exception.ResourceNotFoundException;
import com.example.miniatures.model.MiniatureSale;
import com.example.miniatures.model.enums.MiniatureScale;
import com.example.miniatures.model.enums.MiniatureType;
import com.example.miniatures.repository.MiniatureSaleRepository;
import com.example.miniatures.specification.MiniatureSaleSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MiniatureSaleService {

    private final MiniatureSaleRepository miniatureSaleRepository;

    public MiniatureSaleService(MiniatureSaleRepository miniatureSaleRepository) {
        this.miniatureSaleRepository = miniatureSaleRepository;
    }


     // *** Miniature sales find/get methods ***

    /*
     * Main Method to help you to obtain sales saved in database.
     */
    public List<MiniatureSale> getSales(
            MiniatureType type,
            MiniatureScale scale,
            String clientName,
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

        if(clientName != null) {
            spec = spec.and(MiniatureSaleSpecifications.clientNameContains(clientName));
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

        return miniatureSaleRepository.findAll(spec);

    }

    /*
     * Gets a specific sale by its id.
     */
    public MiniatureSale getSaleById(long id) {
        return miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature Sale with ID " + id + " not found"));
    }

    /*
     * Gets the last sale of a client.
     */
    public MiniatureSale getLastSaleByClientName(String clientName) {
        return miniatureSaleRepository.findTopByClientNameOrderBySaleDateDesc(clientName)
                .orElseThrow( () -> new ResourceNotFoundException("Miniature Sale of client:  " + clientName + " not found"));
    }

    /*
     * Gets the last 10 sales
     */
    public List<MiniatureSale> getLastSales(){
        List<MiniatureSale> sales = miniatureSaleRepository.findTop10ByOrderBySaleDateDesc();
        throwExceptionIfEmpty(sales, "Sales not found");
        return sales;
    }

    /*
     * Create a new Miniature.
     */
    public MiniatureSale createMiniatureSale(MiniatureSale miniatureSale) {
        return miniatureSaleRepository.save(miniatureSale);
    }

    public Optional<MiniatureSale> getMiniatureSaleById(Long id) {
        return miniatureSaleRepository.findById(id);
    }

    public MiniatureSale updateMiniatureSale(MiniatureSale miniatureSale, Long id) {
        return miniatureSaleRepository.findById(id)
                .map(MiniatureSale->{
                    MiniatureSale.setName(miniatureSale.getName());
                    MiniatureSale.setPrice(miniatureSale.getPrice());
                    MiniatureSale.setSaleDate(LocalDate.now());
                    MiniatureSale.setType(miniatureSale.getType());
                    MiniatureSale.setScale(miniatureSale.getScale());
                    return miniatureSaleRepository.save(MiniatureSale);
                })
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature sale with ID " + id + " not found"));
    }

    public void deleteMiniatureSale(Long id) {
        MiniatureSale sale = miniatureSaleRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Miniature sale with ID " + id + " not found"));
        miniatureSaleRepository.delete(sale);
    }

    private static void throwExceptionIfEmpty(List<MiniatureSale> sales, String clientName) {
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(clientName);
        }
    }

}
