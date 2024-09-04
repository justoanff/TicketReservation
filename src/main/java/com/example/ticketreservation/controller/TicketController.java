package com.example.ticketreservation.controller;

import com.example.ticketreservation.entity.Ticket;
import com.example.ticketreservation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/available-tickets")
    public List<Ticket> getAllAvailableTickets() {
        return ticketService.getAllAvailableTickets();
    }

}
