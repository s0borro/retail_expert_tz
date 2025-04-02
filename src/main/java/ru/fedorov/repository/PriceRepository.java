package ru.fedorov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedorov.model.Price;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Поиск цены по названию сети и коду материала
     *
     * @param chainName  название сети
     * @param materialNo код материала
     * @return {@link Optional} с найденной ценой или empty, если цена не найдена
     */
    Optional<Price> findByChainNameAndMaterialNo(String chainName, String materialNo);
}
