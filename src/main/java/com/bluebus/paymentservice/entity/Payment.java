package com.bluebus.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public String getPaymentnumber() {
        return paymentnumber;
    }

    public void setPaymentnumber(String paymentnumber) {
        this.paymentnumber = paymentnumber;
    }

    public String getBookingnumber() {
        return bookingnumber;
    }

    public void setBookingnumber(String bookingnumber) {
        this.bookingnumber = bookingnumber;
    }

    public String getDateofpayment() {
        return dateofpayment;
    }

    public void setDateofpayment(String dateofpayment) {
        this.dateofpayment = dateofpayment;
    }

}