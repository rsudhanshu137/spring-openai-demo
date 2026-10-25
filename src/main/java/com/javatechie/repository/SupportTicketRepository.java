package com.javatechie.repository;

import com.javatechie.entity.SupportTicket;
import com.javatechie.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket,Long> {

    List<SupportTicket> findByStatus(TicketStatus status);
    List<SupportTicket> findByAssignedTo(String assignedTo);


}
