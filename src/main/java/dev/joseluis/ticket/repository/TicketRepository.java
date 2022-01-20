package dev.joseluis.ticket.repository;

import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Integer> getTicketByStatusIsTrueAndSupportIs(Integer supportId);
    Optional<Ticket> getTicketByIdAndCustomer(Integer id, User customer);

    @Modifying
    @Query("UPDATE Ticket SET status = false WHERE id = ?1 AND (customer = ?2 OR support =?2)")
    @Transactional
    void closeTicket(int ticketId, User customer);

    @Modifying
    @Query("UPDATE Ticket SET support = ?2 WHERE id = ?1")
    @Transactional
    void linkSupportToTicket(int ticketId, User support);

    Optional<Ticket> getTicketByIdAndSupport(int id, User support);
    List<Ticket> findAllBySupport(User support);
    List<Ticket> findTicketsByStatusIsTrueAndSupportIsNull();
}
