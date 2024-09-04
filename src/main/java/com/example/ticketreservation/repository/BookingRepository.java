package com.example.ticketreservation.repository;

import com.example.ticketreservation.entity.Booking;
import com.example.ticketreservation.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
