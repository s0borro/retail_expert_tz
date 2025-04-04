package ru.fedorov.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String chainName;

    String materialNo;

    Double regularPricePerUnit;
}
