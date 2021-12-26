package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping("/root/activate")
    public String getActivate(@ModelAttribute("user") User user){
        return "root/activate-root";
    }

    @PostMapping("/root/activate")
    public String postActivate(@ModelAttribute("user") User user){
        try {
            userService.createUserByRoot(user);
        } catch (UserException ex) {
            logger.error("POST /root/activate: " + ex.getMessage());
            if(ex.getCause() != null){
                logger.error("caused by: " + ex.getCause());
            }
            return "redirect:/root/activate?error";
        }
        return "redirect:/root/activate?success";
    }
}
