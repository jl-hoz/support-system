package dev.joseluis.ticket.controller;

import dev.joseluis.ticket.exception.ServiceException;
import dev.joseluis.ticket.model.Service;
import dev.joseluis.ticket.service.ServiceServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServiceController {

    @Autowired
    private ServiceServ serviceServ;

    private Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @GetMapping("/service/new")
    public String getNewService(@ModelAttribute Service service){
        return "new-service";
    }

    @PostMapping("/service/new")
    public String postNewService(@ModelAttribute Service service){
        try {
            serviceServ.newService(service);
        } catch (ServiceException e) {
            logger.error("POST /service/edit-status: " +e.getMessage());
            if(e.getCause() != null) {
                logger.error("caused by: " + e.getCause());
                return "redirect:/service/new?error";
            }
        }
        return "redirect:/service/new?success";
    }

    @GetMapping("/service/edit-status")
    public String getToggleService(@ModelAttribute Service service){
        return "toggle-service";
    }

    @PostMapping("/service/edit-status")
    public String postToggleService(@ModelAttribute Service service){
        try {
            serviceServ.toggleStatus(service);
        } catch (ServiceException e) {
            logger.error("POST /service/edit-status: " +e.getMessage());
            if(e.getCause() != null){
                logger.error("caused by: " + e.getCause());
            }
            return "redirect:/service/edit-status?error";
        }

        return "redirect:/service/edit-status?success";
    }
}
