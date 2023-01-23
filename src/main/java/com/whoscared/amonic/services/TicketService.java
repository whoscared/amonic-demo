package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.info.Ticket;
import com.whoscared.amonic.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketService ticketService,
                         TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    public void save (Ticket ticket){
        ticketRepository.save(ticket);
    }
}
