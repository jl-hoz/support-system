package dev.joseluis.ticket.repository;

import dev.joseluis.ticket.model.Message;
import dev.joseluis.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findMessagesByMessageIs(Ticket ticket);
}
