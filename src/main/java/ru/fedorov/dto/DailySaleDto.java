package ru.fedorov.dto;

import java.time.LocalDate;

public record DailySaleDto (

        LocalDate date,
        String chainName,
        String productName,
        Integer volume,
        Double salesValue,
        String promoFlag
) {
}
