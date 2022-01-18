package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.model.Message;
import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.MessageService;
import dev.joseluis.ticket.service.TicketService;
import dev.joseluis.ticket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.time.Instant;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PostMapping("/ticket/{ticket_id}/send-message/")
    public String postSendMessage(@PathVariable String ticket_id, @ModelAttribute Message message){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User userPrincipal = null;
            userPrincipal = userService.getUserByEmail(userDetails.getUsername());
            message.setUser(userPrincipal);
            message.setCreated(Instant.now());
            Ticket ticket = ticketService.get(Integer.parseInt(ticket_id))
                            .orElseThrow(() -> new RuntimeException("Ticket not found"));
            message.setMessage(ticket);
            messageService.sendMessage(message);
            logger.info(message.toString());
        } catch (Exception e) {
            logger.error("GET /ticket/" + ticket_id + ": " + e.getMessage());
            if(e.getCause() != null){
                logger.error("caused by: " + e.getCause());
            }
            return "error";
        }

        return "redirect:/ticket/" + ticket_id;
    }

}
