package com.example.ticketreservation.service;

import com.example.ticketreservation.entity.Ticket;
import com.example.ticketreservation.entity.TicketStatus;
import com.example.ticketreservation.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllAvailableTickets(){
        return ticketRepository.findByTicketStatus(TicketStatus.AVAILABLE);
    }
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

}
