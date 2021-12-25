package dev.joseluis.ticket.service;

import dev.joseluis.ticket.exception.UserException;
import dev.joseluis.ticket.model.User;
import dev.joseluis.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUserByAdmin(User user) throws UserException {
        try {
            // Check if email already exists
            boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
            if(userExists)
                throw new UserException("email already exists");
            user.setPassword(UUID.randomUUID().toString());
            // Admins can create any type of user, except admin and root user
            if(user.getRole().contentEquals("ROLE_ROOT") || user.getRole().contentEquals("ROLE_ADMIN"))
                throw new UserException("user do not have necessary permissions for this operation");
            // Save user to database
            userRepository.save(user);
            // Show generated password
            System.out.println("email: " + user.getEmail() + " | generated password: " + user.getPassword());
        } catch (UserException e) {
            throw e;
        } catch (Exception e){
            throw new UserException("error creating a new user", e);
        }
    }

    public void createUserByRoot(User user) throws UserException {
        try {
            // Check if email already exists
            boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
            if(userExists)
                throw new UserException("email already exists");
            user.setPassword(UUID.randomUUID().toString());
            // Root can create any type of user, except root
            if(user.getRole().contentEquals("ROLE_ROOT"))
                throw new UserException("user do not have necessary permissions for this operation");
            // Save user to database
            userRepository.save(user);
            // Show generated password
            System.out.println("email: " + user.getEmail() + " | generated password: " + user.getPassword());
        } catch (UserException e) {
            throw e;
        } catch (Exception e){
            throw new UserException("error creating a new user", e);
        }
    }

//
//    public void updateUser(User user){
//        repository.save(user);
//    }
//
//    public User getUserById(int id){
//        return repository.getById(id);
//    }
//
//    public User getUserByIdAndPassword(String email, String password){
//        User user = repository.getByEmail(email, password);
//        return user;
//    }
//
//    public void deleteUser(int id){
//        repository.deleteById(id);
//    }

}
