package com.bluebus.paymentservice.controller;

import com.bluebus.paymentservice.entity.Booking;
import com.bluebus.paymentservice.entity.Payment;
import com.bluebus.paymentservice.repo.BookingRepository;
import com.bluebus.paymentservice.repo.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("api/v1")
@Component
public class PaymentServiceController {
    PaymentRepository paymentRepository;
    BookingRepository bookingRepository;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceController.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    PaymentServiceController(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @JmsListener(destination = "busservice")
    public void receiveMessage(String message) throws JsonProcessingException {
        LOGGER.info("Received message: {}", message);
        Booking booking = new Gson().fromJson(message, Booking.class);
        LOGGER.info("Message Consumed   {}",booking.getBusnumber());
        Payment payment = new Payment();
        payment.setBookingnumber(booking.getBookingnumber());
        addPayment(payment);
        jmsTemplate.convertAndSend("payment-service-queue", message);
    }

    @PostMapping("addPayment")
    public ResponseEntity<String> addPayment(@RequestBody Payment payment) {
        Random rand = new Random();
        payment.setPaymentnumber(String.valueOf(rand.nextInt(1000)));
        payment.setBookingnumber(payment.getBookingnumber());
        LocalDateTime myObj = LocalDateTime.now();
        payment.setDateofpayment(myObj.format(myFormatObj));
        paymentRepository.save(payment);
        return ResponseEntity.ok("Payment Added");
    }

    @PutMapping("editPayment")
    public ResponseEntity<String> editPayment(@RequestBody Payment payment) {
        paymentRepository.save(payment);
        return ResponseEntity.ok("Edited Payment");
    }

    @DeleteMapping("deletePayment/{paymentNumber}")
    public ResponseEntity<String> deletePayment(@PathVariable String paymentNumber) {
        paymentRepository.deleteById(paymentNumber);
        return ResponseEntity.ok("Deleted Payment");
    }

    @GetMapping("fetchPayment/{paymentNumber}")
    public ResponseEntity<Optional<Payment>> fetchPayment(@PathVariable String paymentNumber) {
        return ResponseEntity.ok(paymentRepository.findById(paymentNumber));
    }
}