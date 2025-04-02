package ru.fedorov.service;

import ru.fedorov.dto.DailySaleDto;
import ru.fedorov.dto.SaleWithPromoDto;

import java.util.List;

public interface PromoCalculationService {

    /**
     * Метод для выгрузки фактов продаж с признаком промо
     */
    List<SaleWithPromoDto> getSalesWithPromoFlag();

    /**
     * Метод для выгрузки фактов по дням с фильтрацией
     */
    List<DailySaleDto> getDailySales(List<String> chains, List<String> products);
}
