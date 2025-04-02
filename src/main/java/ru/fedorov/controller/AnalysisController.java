package ru.fedorov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fedorov.dto.DailySaleDto;
import ru.fedorov.dto.SaleWithPromoDto;
import ru.fedorov.service.PromoCalculationService;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final PromoCalculationService promoCalculationService;

    @GetMapping("/sales-with-promo")
    public List<SaleWithPromoDto> salesWithPromoFlag() {
        return promoCalculationService.getSalesWithPromoFlag();
    }

    @GetMapping("/daily-sales")
    public List<DailySaleDto> getDailySales(
            @RequestParam(required = false) List<String> chains,
            @RequestParam(required = false) List<String> products) {

        return promoCalculationService.getDailySales(chains, products);
    }
}
