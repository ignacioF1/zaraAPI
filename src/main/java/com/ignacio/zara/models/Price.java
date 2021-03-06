package com.ignacio.zara.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    private Integer priceList;
    @Column(nullable = false)
    private Integer brandId;
    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;
    @Column(nullable = false)
    private Integer productId;
    @Column(nullable = false)
    private Integer priority;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String currency;
}
