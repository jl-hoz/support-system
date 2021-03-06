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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceServ serviceRepository;

    private TicketService ticketService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public String home(ModelMap model){
        try{
            String role = getPrincipalRole();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User userPrincipal = userService.getUserByEmail(userDetails.getUsername());
            if(role.contains("ADMIN")){
                // Get appropiate users and remove ROLE_ prefix from all users
                List<User> userList = userService.getUsersByAdmin()
                        .stream().map(user -> {
                            user.setRole(user.getRole().substring(5).toLowerCase());
                            return user;
                        }).collect(Collectors.toList());
                model.addAttribute("userList", userList);
            }else if(role.contains("ROOT")){
                // Get appropiate users and remove ROLE_ prefix from all users
                List<User> userList = userService.getUsersByRoot()
                        .stream().map(user -> {
                            user.setRole(user.getRole().substring(5).toLowerCase());
                            return user;
                        }).collect(Collectors.toList());
                model.addAttribute("userList", userList);
            }else if(role.contains("ANALYST")){
                List<Service> serviceList = serviceRepository.getServices();
                model.addAttribute("serviceList", serviceList);
            }else if(role.contains("CUSTOMER")){
                List<Ticket> ticketList = ticketService.findAll().stream()
                        .filter(ticket -> ticket.getCustomer().getId() == userPrincipal.getId())
                        .collect(Collectors.toList());
                model.addAttribute("ticketList", ticketList);
            }else if(role.contains("SUPPORT")){
                List<Ticket> ticketList = ticketService.findAllBySupport(userPrincipal).stream()
                        .filter(ticket -> ticket.getSupport().getId() == userPrincipal.getId())
                        .collect(Collectors.toList());
                model.addAttribute("ticketList", ticketList);
            }
        }catch (Exception ex){
            logger.error("GET /: " + ex.getMessage());
            if(ex.getCause() != null){
                logger.error("caused by: " + ex.getCause());
            }
            return "error";
        }

        return "index";
    }

    @RequestMapping("/activate")
    public String getActivate(@ModelAttribute("user") User user){
        return "activate";
    }

    @PostMapping("/activate")
    public String postActivate(@ModelAttribute("user") User user){
        try {
            user.setActive(true);
            String role = getPrincipalRole();
            if(role.contains("ROOT")){
                userService.createUserByRoot(user);
            }else if(role.contains("ADMIN")){
                userService.createUserByAdmin(user);
            }
        } catch (UserException ex) {
            logger.error("POST /activate: " + ex.getMessage());
            if(ex.getCause() != null){
                logger.error("caused by: " + ex.getCause());
            }
            return "redirect:/activate?error";
        }
        return "redirect:/activate?success";
    }

    @GetMapping("/deactivate")
    public String getDeactivate(@ModelAttribute("user") User user){
        return "deactivate";
    }

    @PostMapping("/deactivate")
    public String postDeactivate(@ModelAttribute User user){
        try {
            String role = getPrincipalRole();
            if(role.contains("ROOT")){
                userService.deactivateUserByRoot(user);
            }else if(role.contains("ADMIN")){
                userService.deactivateUserByAdmin(user);
            }
        } catch (UserException ex) {
            logger.error("POST /deactivate: " + ex.getMessage());
            if(ex.getCause() != null){
                logger.error("caused by: " + ex.getCause());
            }
            return "redirect:deactivate?error";
        }

        return "redirect:deactivate?success";
    }

    private String getPrincipalRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return String.valueOf(authentication.getAuthorities());
    }


    @GetMapping("/profile")
    public String profile(ModelMap model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.getUserByEmail(userDetails.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("role", user.getRole().substring(5).toLowerCase());
        } catch (UserException e) {
            logger.error("ERROR GET /profile: " + e.getMessage());
            if(e.getCause() != null){
                logger.error("\t caused by: " + e.getCause());
            }
            return "error";
        }
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String getEditProfile(@ModelAttribute User user, ModelMap model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.getUserByEmail(userDetails.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("surname", user.getSurname());
        } catch (UserException e) {
            logger.error("GET /profile/edit: " + e.getMessage());
            if(e.getCause() != null){
                logger.error("caused by: " + e.getCause());
            }
            return "error";
        }
        return "edit-profile";
    }

    @PostMapping ("/profile/edit")
    public String postEditProfile(@ModelAttribute User user) {
        try {
            userService.updateProfile(user);
        } catch (UserException e) {
            logger.error("POST /profile/edit: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("caused by: " + e.getCause());
            }
            return "redirect:/profile/edit?error";
        }
        return "redirect:/logout";
    }


    @GetMapping("/profile/change-password")
    public String getChangePassword(@ModelAttribute User user, ModelMap model){
        return "change-password";
    }

    @PostMapping ("/profile/change-password")
    public String postChangePassword(@ModelAttribute User user) {
        try {
            userService.changePassword(user);
        } catch (UserException e) {
            logger.error("POST /profile/change-password: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("caused by: " + e.getCause());
            }
            return "error";
        }
        return "redirect:/logout";
    }

    @Autowired
    private void setTicketService(TicketService ticketService){
        this.ticketService = ticketService;
    }
}
