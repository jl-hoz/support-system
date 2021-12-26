package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public String profile(@ModelAttribute("user") User user, ModelMap model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.getUserByEmail(userDetails.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("role", user.getRole().substring(5).toLowerCase());
        } catch (UserException e) {
            System.err.println("ERROR GET /profile: " + e.getMessage());
            if(e.getCause() != null){
                System.err.println("\t caused by: " + e.getCause());
            }
            return "index";
        }
        return "profile";
    }

}
