package com.bluebus.paymentservice.service;

import com.bluebus.paymentservice.entity.Booking;
import com.bluebus.paymentservice.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class JMSCustomListener {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSCustomListener.class);

    @JmsListener(destination = "busservice")
    public void receiveMessage(String message) throws JsonProcessingException, InterruptedException {
        LOGGER.info("Received message from busservice: {}", message);
        Thread.sleep(1000); // Simulate processing delay
        Booking booking = new Gson().fromJson(message, Booking.class);
        LOGGER.info("Message Consumed   {}",booking.getBusnumber());
        Payment payment = new Payment();
        payment.setBookingnumber(booking.getBookingnumber());
        paymentService.createPayment(payment);
        jmsTemplate.convertAndSend("payment-service-queue", message);
    }
}
