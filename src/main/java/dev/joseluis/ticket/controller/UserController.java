package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public String getCreateClient(@ModelAttribute("user") User user){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String postCreateClient(@ModelAttribute("user") User user){
        user.setRole("client");
        user.setPermissions("default");
        userService.createUser(user);
        System.out.println(user);
        return "redirect:/login";
    }

    @RequestMapping("/loginn")
    public String getLogin(@ModelAttribute("user") User user){
        return "login";
    }

    @PostMapping("/loginn")
    public String postLogin(@ModelAttribute("user") User user){
        User login = userService.getUserByIdAndPassword(user.getEmail(), user.getPassword());
        System.out.println(login);
        return "redirect:/profile";
    }

}