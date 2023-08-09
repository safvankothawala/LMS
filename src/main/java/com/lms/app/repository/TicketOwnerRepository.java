package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.TicketOwner;

@Repository
public interface TicketOwnerRepository extends JpaRepository<TicketOwner, Long> {

	TicketOwner findByTicketOwnerIdentity(String ticketOwnerIdentity);
}
