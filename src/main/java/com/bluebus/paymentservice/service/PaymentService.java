package com.bluebus.paymentservice.service;

import com.bluebus.paymentservice.entity.Payment;
import com.bluebus.paymentservice.exception.ResourceNotFoundException;
import com.bluebus.paymentservice.repo.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthValidationService authValidationService;

    public void createPayment(Payment payment) {
        LOGGER.info("Creating payment for booking number: {}", payment.getBookingnumber());
        Random rand = new Random();
        payment.setPaymentnumber(String.valueOf(rand.nextInt(1000)));
        payment.setBookingnumber(payment.getBookingnumber());
        payment.setDateofpayment(getDateTime());
        payment.setCreationdate(getDateTime());
        payment.setLastupdateddate(getDateTime());
        paymentRepository.save(payment);
        LOGGER.info("Payment created with payment number: {}", payment.getPaymentnumber());
    }

    public Optional<Payment> fetchPayment(String paymentNumber) {
        return Optional.ofNullable(paymentRepository.findById(paymentNumber).orElseThrow(() -> {
            LOGGER.error("Payment with payment number: {} not found", paymentNumber);
            return new ResourceNotFoundException("Payment not found");
        }));
    }

    public void updatePayment(Payment payment) {
        LOGGER.info("Updating payment with payment number: {}", payment.getPaymentnumber());
        Payment existingPayment = fetchPayment(payment.getPaymentnumber()).orElseThrow();
        existingPayment.setBookingnumber(payment.getBookingnumber());
        existingPayment.setDateofpayment(payment.getDateofpayment());
        existingPayment.setLastupdateddate(getDateTime());
        paymentRepository.save(existingPayment);
        LOGGER.info("Payment updated with payment number: {}", payment.getPaymentnumber());
    }
    public void deletePayment(String paymentNumber) {
        LOGGER.info("Deleting payment with payment number: {}", paymentNumber);
        paymentRepository.deleteById(paymentNumber);
        LOGGER.info("Payment deleted with payment number: {}", paymentNumber);
    }

    private String getDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(dateTimeFormatter);
    }

    public boolean validateToken(String token) {
        LOGGER.info("Validating token: {}", token);
        // For simplicity, let's assume a valid token is "valid-token"
        if (token == null || !authValidationService.validateToken(token)) {
            LOGGER.warn("Invalid token provided: {}", token);
            return false;
        }
        LOGGER.info("Token is valid");
        return true;
    }
}
