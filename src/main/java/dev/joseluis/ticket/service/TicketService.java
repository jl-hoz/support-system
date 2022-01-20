package dev.joseluis.ticket.service;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.repository.TicketRepository;
import dev.joseluis.ticket.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public void open(Ticket ticket){
        ticket.setCreated(Instant.now());
        ticket.setStatus(true);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException());
        ticket.setCustomer(user);
        ticketRepository.save(ticket);
    }

    public Optional<Ticket> get(int ticketID){
        return ticketRepository.findById(ticketID);
    }

    public void close(int ticketId, User user) {
        ticketRepository.closeTicket(ticketId, user);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public List<Ticket> findAllBySupport(User support){
        return ticketRepository.findAllBySupport(support);
    }

    public Optional<Ticket> findByIdCustomer(int id, User customer){
        return ticketRepository.getTicketByIdAndCustomer(id, customer);
    }

    public Optional<Ticket> findByIdSupport(int id, User support){
        return ticketRepository.getTicketByIdAndSupport(id, support);
    }

    @Scheduled(cron = "1 * * * * *")
    public void linkTicketWithSupport() {
        try {
            List<User> supportAvailable = userRepository.findAllByRoleContainingAndActiveIsTrue("ROLE_SUPPORT");
            if(supportAvailable.isEmpty()){
                throw new UserException("There is no support users to link to new tickets");
            }
            List<Ticket> ticketsToLink = ticketRepository.findTicketsByStatusIsTrueAndSupportIsNull();
            ticketsToLink.forEach(ticket -> ticketRepository.linkSupportToTicket(ticket.getId(), supportAvailable.get(randomInteger(0, supportAvailable.size()-1))));
            logger.info("New tickets available: " + ticketsToLink.size() + " | Linked " + ticketsToLink.size() + " tickets with support users");
        } catch (Exception e) {
            logger.error("linkTicketWithSupport: " + e.getMessage());
            if(e.getCause() != null){
                logger.error("caused by: " + e.getCause());
            }
        }
    }

    private static int randomInteger(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Autowired
    private void getTicketRepository(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    private void getUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

}