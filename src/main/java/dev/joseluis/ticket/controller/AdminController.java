package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/activate")
    public String getActivate(@ModelAttribute("user") User user){
        return "admin/activate-admin";
    }

    @PostMapping("/admin/activate")
    public String postActivate(@ModelAttribute("user") User user){
        try {
            userService.createUserByAdmin(user);
        } catch (UserException ex) {
            System.err.println("ERROR POST /activate: " + ex.getMessage());
            if(ex.getCause() != null){
                System.err.println("\t caused by: " + ex.getCause());
            }
            return "redirect:/admin/activate?error";
        }
        return "redirect:/admin/activate?success";
    }
}
