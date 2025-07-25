package com.bluebus.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "paymentnumber", nullable = false, length = Integer.MAX_VALUE)
    private String paymentnumber;

    @Column(name = "bookingnumber", length = Integer.MAX_VALUE)
    private String bookingnumber;

    @Column(name = "dateofpayment", length = Integer.MAX_VALUE)
    private String dateofpayment;

}