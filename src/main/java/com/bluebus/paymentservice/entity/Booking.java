package com.bluebus.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "booking")
public class Booking implements Serializable {
    @Id
    @Column(name = "bookingnumber", nullable = false, length = Integer.MAX_VALUE)
    private String bookingnumber;

    @Column(name = "busnumber", length = Integer.MAX_VALUE)
    private String busnumber;

    @Column(name = "bookingdate", length = Integer.MAX_VALUE)
    private String bookingdate;

    @Column(name = "source", length = Integer.MAX_VALUE)
    private String source;

    @Column(name = "destination", length = Integer.MAX_VALUE)
    private String destination;

    @Column(name = "numberofseats", length = Integer.MAX_VALUE)
    private String numberofseats;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

}