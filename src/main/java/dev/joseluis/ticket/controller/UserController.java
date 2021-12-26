package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.User;
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

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/activate")
    public String getActivate(@ModelAttribute("user") User user){
        return "activate";
    }

    @PostMapping("/activate")
    public String postActivate(@ModelAttribute("user") User user){
        try {
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

}
