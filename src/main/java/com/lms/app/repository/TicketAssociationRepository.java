package com.lms.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Customer;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketAssociation;
import com.lms.app.entity.TicketOwner;

@Repository
public interface TicketAssociationRepository extends JpaRepository<TicketAssociation, Long> {

	TicketAssociation findByTicketOwner(TicketOwner ticketOwner);

	List<TicketAssociation> findByCustomer(Customer customer);

	TicketAssociation findByTicket(Ticket winnerTicket);

	List<TicketAssociation> findByWinner(boolean winner);

}
