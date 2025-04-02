package ru.fedorov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedorov.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
