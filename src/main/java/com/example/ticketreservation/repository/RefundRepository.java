package com.example.ticketreservation.repository;

import com.example.ticketreservation.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefundRepository extends JpaRepository<Refund, Long> {
}
