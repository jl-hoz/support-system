package dev.joseluis.ticket.service;

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
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        // TODO: ticket.setSupport(getAvailableSupport());
        ticketRepository.save(ticket);
        logger.info(ticket.toString());
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

//    @Scheduled(cron = "*/10 * * * * *")
//    public void linkTicketWithSupport() {
//        logger.info("Current time is :: " + Calendar.getInstance().getTime());
//        List<User> supportAvailable = userRepository.findAllByRoleContainingAndActiveIsTrue("ROLE_SUPPORT");
//        supportAvailable.forEach((support) -> {
//            int availableOpenTickets = ticketRepository.getTicketByStatusIsTrueAndSupportIs(support.getId())
//                    .orElse(0);
//            support.setAvailableTickets(availableOpenTickets);
//        });
//        supportAvailable.sort(Comparator.comparing(User::getAvailableTickets));
//        List<Ticket> ticketsNotLinked;
//    }


//    public User getAvailableSupport(){
//        List<User> supportAvailable = userRepository.findAllByRoleContainingAndActiveIsTrue("ROLE_SUPPORT");
//        supportAvailable.stream().forEach((support) -> {
//            int availableOpenTickets = ticketRepository.getTicketByStatusIsTrueAndSupportIs(support.getId())
//                    .orElse(0);
//            support.setAvailableTickets(availableOpenTickets);
//        });
//        supportAvailable.sort(Comparator.comparing(User::getAvailableTickets));
//        return supportAvailable.stream().findFirst().orElseThrow(() -> new RuntimeException("No support users"));
//    }

    @Autowired
    private void getTicketRepository(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    private void getUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

}