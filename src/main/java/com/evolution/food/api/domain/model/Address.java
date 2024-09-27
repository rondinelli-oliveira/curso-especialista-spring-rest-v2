package com.evolution.food.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @Column(name = "address_zip_code")
    private String zipCode;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private int number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_district")
    private String district;

    @ManyToOne
    @JoinColumn(name = "address_city_id")
    private City city;

}
