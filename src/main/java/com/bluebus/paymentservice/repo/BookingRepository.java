package com.bluebus.paymentservice.repo;

import com.bluebus.paymentservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}