package com.evolution.food.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Ordered {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subTotal;
    private BigDecimal freightRate;
    private BigDecimal totalValue;

    @Embedded
    private Address deliveryAddress;

    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    private LocalDateTime confirmationDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "ordered")
    private List<OrderedItem> itens = new ArrayList<>();
}
