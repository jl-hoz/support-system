package dev.joseluis.ticket.service;

import dev.joseluis.ticket.model.Service;
import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TicketServiceTest {

    @Test
    @DisplayName("Should open new ticket")
    public void shouldOpenTicket(){
        Ticket ticket = new Ticket();
        Service service = new Service();
        service.setActive(true);
        service.setName("TESTING");
        service.setId(1);
        ticket.setId(1);
        ticket.setService(service);
        ticket.setSubject("Subject");
        Assertions.assertNotNull(ticket);
    }

//    @Test
//    @DisplayName("Should not link ticket to support, because there is not support users")
//    /**
//     * Ticket cannot be linked to support user because
//     * there is no support user.
//     */
//    public void shouldNotLinkTicketNoSupport(){
//        // Customer init
//        User customer = new User();
//        customer.setId(1);
//        // Service init
//        Service service = new Service();
//        service.setName("TESTING");
//        service.setId(1);
//        // Ticket init
//        Ticket ticket = new Ticket();
//        ticket.setId(1);
//        ticket.setService(service);
//        ticket.setSubject("TESTING");
//        ticket.setDescription("Testing not support");
//        ticket.setCreated(Instant.now());
//        ticket.setStatus(true);
//        // Try to link with cronjob
//        Assertions.assertTrue(TicketService.linkTicketWithSupport());
//    }
}
