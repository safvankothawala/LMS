package com.lms.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Customer;
import com.lms.app.entity.TicketAssociation;
import com.lms.app.entity.TicketOwner;

@Repository
public interface TicketAssociationRepository extends JpaRepository<TicketAssociation, Long> {

	@Query("select ta from TicketAssociation ta where ta.ticket= :ticketNumber")
	TicketAssociation getByTicketNumber(@Param("ticketNumber") String ticketNumber);

	TicketAssociation findByTicketOwner(TicketOwner ticketOwner);

	List<TicketAssociation> findByCustomer(Customer customer);

}
