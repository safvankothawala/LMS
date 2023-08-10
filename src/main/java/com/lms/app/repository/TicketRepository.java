package com.lms.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Draw;
import com.lms.app.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Ticket findByTicketNumber(String ticketNumber);

	List<Ticket> findByDraw(Draw draw);
	
	List<Ticket> findByDrawAndAvailable(Draw draw, boolean available);
}
