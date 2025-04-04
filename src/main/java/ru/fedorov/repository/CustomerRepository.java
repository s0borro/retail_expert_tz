package ru.fedorov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedorov.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
