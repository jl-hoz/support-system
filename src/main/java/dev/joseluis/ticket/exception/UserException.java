package dev.joseluis.ticket.exception;

public class UserException extends Exception{

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Exception ex){
        super(message, ex);
    }
}
