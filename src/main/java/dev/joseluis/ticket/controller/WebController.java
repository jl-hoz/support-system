package dev.joseluis.ticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String home(){
        return ("<h1>Home</h1>");
    }

    @GetMapping("/ticket")
    public String ticket(){
        return ("<h1>Ticket</h1>");
    }

    @GetMapping("/profile")
    public String profile(){
        return ("<h1>Profile</h1>");
    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h1>Admin</h1>");
    }
}
