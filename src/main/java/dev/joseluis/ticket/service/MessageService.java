package dev.joseluis.ticket.service;

import dev.joseluis.ticket.model.Message;
import dev.joseluis.ticket.model.Ticket;
import dev.joseluis.ticket.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Message message){
        messageRepository.save(message);
    }

    public List<Message> getMessages(Ticket ticket) {
        return messageRepository.findMessagesByMessageIs(ticket);
    }
}
