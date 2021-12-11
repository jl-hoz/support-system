package dev.joseluis.ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @GetMapping("/")
    public String getHome() { return "index"; }

    @GetMapping("/ticket/create")
    public String getCreate(){
        return "create";
    }

    @GetMapping("/ticket/{id}")
    public ModelAndView getTicket(){
        return new ModelAndView("ticket");
    }
}
