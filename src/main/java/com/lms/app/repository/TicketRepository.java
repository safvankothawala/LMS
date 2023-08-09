package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Ticket findByTicketNumber(String ticketNumber);
}
