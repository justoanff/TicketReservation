package com.example.ticketreservation.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "quantity")
    private int quantity;

    @CreationTimestamp
    @Column(name = "booking_time")
    private Date bookingTime;

    @Column(name = "confirmation_time")
    private Date confirmedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Payment payment;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Refund refund;

    public boolean isTimeLimitExceeded(long timeLimitInMillis) {
        long currentTime = System.currentTimeMillis();
        long bookingTimeMillis = bookingTime.getTime();
        return (currentTime - bookingTimeMillis) > timeLimitInMillis;
    }
}
