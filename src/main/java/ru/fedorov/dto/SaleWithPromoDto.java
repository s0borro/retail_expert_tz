package ru.fedorov.dto;

public record SaleWithPromoDto(

        String chainName,
        String productCategory,
        String month,
        Integer regularSalesVolume,
        Integer promoSalesVolume,
        Double promoShare
) {
}
