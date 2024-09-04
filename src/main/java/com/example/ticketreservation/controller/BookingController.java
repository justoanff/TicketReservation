package com.example.ticketreservation.controller;

import com.example.ticketreservation.entity.Booking;
import com.example.ticketreservation.entity.PaymentMethod;
import com.example.ticketreservation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookTicket(@RequestParam Long userId, @RequestParam Long ticketId, @RequestParam int quantity) {
        Booking booking = bookingService.bookTicket(userId, ticketId, quantity);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Booking> confirmBooking(@RequestParam Long bookingId, @RequestParam PaymentMethod paymentMethod) {
        Booking booking = bookingService.confirmBooking(bookingId, paymentMethod);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Booking> cancelBooking(@RequestParam Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
}
