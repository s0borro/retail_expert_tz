package ru.fedorov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fedorov.model.Price;
import ru.fedorov.repository.PriceRepository;

import java.util.List;

@RestController
@RequestMapping("/api/finance/prices")
@RequiredArgsConstructor
public class FinanceController {

    private final PriceRepository priceRepository;

    @GetMapping
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable Long id) {
        return priceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Price createPrice(@RequestBody Price price) {
        return priceRepository.save(price);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable Long id, @RequestBody Price priceDetails) {
        return priceRepository.findById(id)
                .map(price -> {
                    price.setChainName(priceDetails.getChainName());
                    price.setMaterialNo(priceDetails.getMaterialNo());
                    price.setRegularPricePerUnit(priceDetails.getRegularPricePerUnit());
                    return ResponseEntity.ok(priceRepository.save(price));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrice(@PathVariable Long id) {
        return priceRepository.findById(id)
                .map(price -> {
                    priceRepository.delete(price);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
