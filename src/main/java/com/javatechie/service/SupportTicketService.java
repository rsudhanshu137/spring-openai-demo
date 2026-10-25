package com.javatechie.service;

import com.javatechie.entity.Priority;
import com.javatechie.entity.SupportTicket;
import com.javatechie.entity.TicketStatus;
import com.javatechie.repository.SupportTicketRepository;
import com.javatechie.util.TicketHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository ticketRepository;

    public SupportTicket createSupportTicket(String desc,String email){
        SupportTicket ticket=new SupportTicket();
        ticket.setTitle(TicketHelper.generateTitle(desc));
        ticket.setDescription(desc);
        ticket.setCustomerEmail(email);
        ticket.setPriority(TicketHelper.detectPriority(desc));
        ticket.setCategory(TicketHelper.detectCategory(desc));
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setAssignedTo(TicketHelper.assignEngineer(ticket.getCategory()));
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setSLA(TicketHelper.calculateSLA(ticket.getPriority()));
        return ticketRepository.save(ticket);
    }

    public SupportTicket getTicketDetails(Long id){
        return ticketRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Ticket not found with id: " + id));
    }

    public String escalateTicket(Long id){
        SupportTicket supportTicket=getTicketDetails(id);
        supportTicket.setIsEscalated(true);
        supportTicket.setPriority(Priority.HIGH);
        supportTicket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(supportTicket);
        return "Ticket " + id +" escalated to HIGH Priority";
    }

    public String addComment(Long id,String comment){
        SupportTicket supportTicket=getTicketDetails(id);
        supportTicket.getComments().add(comment);
        supportTicket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(supportTicket);
        return "Comment added to ticket" + id;
    }

    public List<SupportTicket> getAllTickets(){
        return ticketRepository.findAll();
    }
}
