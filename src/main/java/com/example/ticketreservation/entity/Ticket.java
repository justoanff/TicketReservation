package com.example.ticketreservation.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "remaining_quantity")
    private int remainingQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus ticketStatus;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Booking> bookings = new HashSet<>();

    public void add(Booking booking) {
        if (booking != null) {

            if (booking == null) {
                bookings = new HashSet<>();
            }

            bookings.add(booking);
            booking.setTicket(this);
        }
    }
}

