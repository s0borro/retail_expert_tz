package ru.fedorov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedorov.dto.DailySaleDto;
import ru.fedorov.dto.SaleWithPromoDto;
import ru.fedorov.model.Customer;
import ru.fedorov.model.Price;
import ru.fedorov.model.Product;
import ru.fedorov.repository.ActualRepository;
import ru.fedorov.repository.CustomerRepository;
import ru.fedorov.repository.PriceRepository;
import ru.fedorov.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromoCalculationServiceImpl implements PromoCalculationService {

    private final ProductRepository productRepository;

    private final ActualRepository actualRepository;

    private final PriceRepository priceRepository;

    private final CustomerRepository customerRepository;

    /**
     * Метод для выгрузки фактов продаж с признаком промо
     */
    @Override
    @Transactional
    public List<SaleWithPromoDto> getSalesWithPromoFlag() {
        return actualRepository.findAll().stream()
                .map(actual -> {
                    Customer customer = customerRepository.findById(actual.getShipToCode())
                            .orElseThrow(() -> new RuntimeException("Customer not found"));

                    Product product = productRepository.findById(actual.getMaterialNo())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    Price price = priceRepository.findByChainNameAndMaterialNo(
                                    customer.getChainName(), actual.getMaterialNo())
                            .orElseThrow(() -> new RuntimeException("Price not found"));

                    double actualPricePerUnit = actual.getActualSalesValue() / actual.getVolume();
                    boolean isPromo = actualPricePerUnit < price.getRegularPricePerUnit();

                    return new SaleWithPromoDto(
                            customer.getChainName(),
                            product.getProductCategoryName(),
                            actual.getDate().getMonth().toString(),
                            isPromo ? 0 : actual.getVolume(),
                            isPromo ? actual.getVolume() : 0,
                            isPromo ? 100.0 : 0.0
                    );
                })
                .collect(Collectors.groupingBy(dto ->
                        new SalesWithPromoKey(dto.chainName(), dto.productCategory(), dto.month())))
                .entrySet().stream()
                .map(entry -> {
                    SalesWithPromoKey key = entry.getKey();
                    List<SaleWithPromoDto> dtos = entry.getValue();

                    int totalRegular = dtos.stream().mapToInt(SaleWithPromoDto::regularSalesVolume).sum();
                    int totalPromo = dtos.stream().mapToInt(SaleWithPromoDto::promoSalesVolume).sum();
                    int totalVolume = totalRegular + totalPromo;
                    double promoShare = totalVolume > 0 ? (totalPromo * 100.0 / totalVolume) : 0.0;

                    return new SaleWithPromoDto(
                            key.chainName(),
                            key.category(),
                            key.month(),
                            totalRegular,
                            totalPromo,
                            promoShare
                    );
                })
                .toList();
    }

    /**
     * Метод для выгрузки фактов по дням с фильтрацией
     */
    @Override
    @Transactional
    public List<DailySaleDto> getDailySales(List<String> chains, List<String> products) {

        return actualRepository.findAll().stream()
                .filter(actual -> {
                    if (Objects.isNull(chains) || chains.isEmpty()) {
                        return true;
                    }
                    return customerRepository.findById(actual.getShipToCode())
                            .map(c -> chains.contains(c.getChainName()))
                            .orElse(false);
                })
                .filter(actual -> Objects.isNull(products) || products.isEmpty() ||
                        products.contains(actual.getMaterialNo()))
                .map(actual -> {
                    Customer customer = customerRepository.findById(actual.getShipToCode())
                            .orElseThrow(() -> new RuntimeException("Customer not found"));

                    Product product = productRepository.findById(actual.getMaterialNo())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    Price price = priceRepository.findByChainNameAndMaterialNo(
                                    customer.getChainName(), actual.getMaterialNo())
                            .orElseThrow(() -> new RuntimeException("Price not found"));

                    double actualPricePerUnit = actual.getActualSalesValue() / actual.getVolume();
                    String promoFlag = actualPricePerUnit < price.getRegularPricePerUnit() ? "Promo" : "Regular";

                    return new DailySaleDto(
                            actual.getDate(),
                            customer.getChainName(),
                            product.getMaterialDesc(),
                            actual.getVolume(),
                            actual.getActualSalesValue(),
                            promoFlag
                    );
                })
                .toList();
    }

    private record SalesWithPromoKey(String chainName, String category, String month) {}
}
