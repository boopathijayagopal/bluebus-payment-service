package com.bluebus.paymentservice.controller;

import com.bluebus.paymentservice.entity.Payment;
import com.bluebus.paymentservice.service.PaymentService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1")
public class PaymentServiceController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceController.class);

    @PostMapping("addPayment")
    public ResponseEntity<String> addPayment(@RequestBody @NonNull Payment payment) {
        LOGGER.info("Adding new payment for booking number: {}", payment.getBookingnumber());
        paymentService.createPayment(payment);
        LOGGER.info("New Payment created with booking number: {}", payment.getBookingnumber());
        return ResponseEntity.ok("New Payment added with number: " + payment.getPaymentnumber());
    }

    @GetMapping("fetchPayment/{paymentNumber}")
    public ResponseEntity<Optional<Payment>> fetchPayment(@PathVariable String paymentNumber) {
        return ResponseEntity.ok(paymentService.fetchPayment(paymentNumber));
    }

    @PutMapping("editPayment")
    public ResponseEntity<String> editPayment(@RequestBody Payment payment) {
        LOGGER.info("Editing payment with number: {}", payment.getPaymentnumber());
        paymentService.updatePayment(payment);
        return ResponseEntity.ok("Edited Payment with number: " + payment.getPaymentnumber());
    }

    @DeleteMapping("deletePayment/{paymentNumber}")
    public ResponseEntity<String> deletePayment(@PathVariable String paymentNumber) {
        paymentService.deletePayment(paymentNumber);
        return ResponseEntity.ok("Deleted Payment with number: " + paymentNumber);
    }


}