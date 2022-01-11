package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.Service;
import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.ServiceServ;
import dev.joseluis.ticket.service.TicketService;
import dev.joseluis.ticket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TicketController {

    private ServiceServ serviceServ;
    private TicketService ticketService;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @GetMapping("/ticket/open")
    public String getOpen(@ModelAttribute Ticket ticket, ModelMap model){
        List<Service> serviceList = serviceServ.getActiveServices();
        model.addAttribute("serviceList", serviceList);
        return "open-ticket";
    }

    @PostMapping("/ticket/open")
    public String postOpen(@ModelAttribute Ticket ticket){
        ticketService.open(ticket);
        return "redirect:/ticket/open?success";
    }

    @GetMapping("/ticket/{id}")
    public String getTicket(@PathVariable String id, ModelMap model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User userPrincipal = null;
            userPrincipal = userService.getUserByEmail(userDetails.getUsername());
            Ticket ticket = null;
            if(userPrincipal.getRole().contentEquals("ROLE_CUSTOMER")){
                ticket = ticketService.findByIdCustomer(Integer.parseInt(id), userPrincipal)
                        .orElseThrow(() -> new RuntimeException("Customer user does not own ticket"));
            }else if(userPrincipal.getRole().contentEquals("ROLE_SUPPORT")){
                ticket = ticketService.findByIdSupport(Integer.parseInt(id), userPrincipal)
                        .orElseThrow(() -> new RuntimeException("Support user does not own ticket"));
            }
            model.addAttribute("ticket", ticket);
        } catch (Exception e) {
            logger.error("GET /ticket/" + id + ": " + e.getMessage());
            if(e.getCause() != null){
                logger.error("caused by: " + e.getCause());
            }
            return "error";
        }
        return "ticket-view";
    }

    @GetMapping("/ticket/close/{id}")
    public String postClose(@PathVariable String id) throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User userPrincipal = userService.getUserByEmail(userDetails.getUsername());
        ticketService.close(Integer.parseInt(id), userPrincipal);
        return "redirect:/ticket/" + id;
    }
    @Autowired
    public void getServiceServ(ServiceServ serviceServ){
        this.serviceServ = serviceServ;
    }

    @Autowired
    public void getTicketService(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @Autowired
    public void getUserService(UserService userService){
        this.userService = userService;
    }
}
