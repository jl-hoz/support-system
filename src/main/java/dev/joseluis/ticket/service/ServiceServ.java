package dev.joseluis.ticket.service;

import dev.joseluis.ticket.exception.ServiceException;
import dev.joseluis.ticket.model.Service;
import dev.joseluis.ticket.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServ {

    @Autowired
    private ServiceRepository serviceRepository;

    public void newService(Service service) throws ServiceException {
        try{
            service.setActive(true);
            serviceRepository.save(service);
        }catch (Exception ex){
            throw new ServiceException("creating a new service", ex);
        }
    }

    public void toggleStatus(Service service) throws ServiceException {
        try {
            Service retrievedService = serviceRepository.findServiceByName(service.getName())
                    .orElseThrow(() -> new ServiceException("service does not exist"));
            serviceRepository.toggleActive(retrievedService.getName(), !retrievedService.getActive());
        }catch (ServiceException ex){
            throw ex;
        }catch (Exception ex){
            throw new ServiceException("changing service status", ex);
        }
    }

    public List<Service> getServices() {
        return serviceRepository.findAll();
    }
}
