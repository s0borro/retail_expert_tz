package ru.fedorov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedorov.model.Actual;

@Repository
public interface ActualRepository extends JpaRepository<Actual, Long> {
}
