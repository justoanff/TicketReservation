package com.example.ticketreservation.service;

import com.example.ticketreservation.entity.*;
import com.example.ticketreservation.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BookingService {

    // Time limit in milliseconds (2 minutes)
    private static final long TIME_LIMIT = 2 * 60 * 1000;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RefundRepository refundRepository;

    // Book tickets
    public Booking bookTicket(Long userId, Long ticketId, int quantity) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            User user = userOptional.get();
            Ticket ticket = ticketOptional.get();

            if (ticket.getRemainingQuantity() >= quantity) {
                Booking booking = new Booking();
                booking.setUser(user);
                booking.setTicket(ticket);
                booking.setBookingStatus(BookingStatus.PENDING);
                booking.setBookingTime(new Date());
                booking.setQuantity(quantity); // Store the quantity

                // Update ticket availability
                ticket.setRemainingQuantity(ticket.getRemainingQuantity() - quantity);
                if (ticket.getRemainingQuantity() == 0) {
                    ticket.setTicketStatus(TicketStatus.BOOKED);
                }
                ticketRepository.save(ticket);

                // Save booking details
                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Requested number of tickets is not available.");
            }
        } else {
            throw new RuntimeException("User or Ticket not found.");
        }
    }


    // Confirm booking by processing payment
    public Booking confirmBooking(Long bookingId, PaymentMethod paymentMethod) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            if (booking.getBookingStatus() == BookingStatus.PENDING) {
                Ticket ticket = booking.getTicket();

                // Use the stored quantity to calculate the amount
                int quantity = booking.getQuantity();
                double amount = ticket.getPrice() * quantity;

                // Create and save the payment
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setPaymentMethod(paymentMethod);
                payment.setBooking(booking);
                paymentRepository.save(payment);

                // Update the booking status to CONFIRMED
                booking.setBookingStatus(BookingStatus.CONFIRMED);
                booking.setConfirmedTime(new Date());

                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Booking is already confirmed or canceled.");
            }
        } else {
            throw new RuntimeException("Booking not found.");
        }
    }


    // Cancel a booking
    public Booking cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            if (booking.getBookingStatus() == BookingStatus.CANCELED) {
                throw new RuntimeException("Booking is already canceled.");
            }

            // Process refund (90% of the total amount)
            Ticket ticket = booking.getTicket();
            int quantity = booking.getQuantity();
            double refundAmount = ticket.getPrice() * quantity * 0.9;

            // Check if a refund already exists
            Refund refund = booking.getRefund();
            if (refund == null) {
                refund = new Refund();
                refund.setBooking(booking);
            }

            refund.setRefundAmount(refundAmount);
            refundRepository.save(refund);

            // Restore ticket availability
            ticket.setRemainingQuantity(ticket.getRemainingQuantity() + quantity);
            ticketRepository.save(ticket);

            // Update booking status to CANCELED
            booking.setBookingStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);

            return booking;
        } else {
            throw new RuntimeException("Booking not found.");
        }
    }

    // This method will run every minute to check for expired bookings
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredBookings() {
        List<Booking> pendingBookings = bookingRepository.findByBookingStatus(BookingStatus.PENDING);

        for (Booking booking : pendingBookings) {
            if (booking.isTimeLimitExceeded(TIME_LIMIT)) {
                cancelBooking(booking.getBookingId());
            }
        }
    }
}