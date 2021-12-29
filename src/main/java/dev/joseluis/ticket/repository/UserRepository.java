package dev.joseluis.ticket.repository;

import dev.joseluis.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleNotContaining(String role);
    List<User> findAllByRoleNotContainingAndRoleNotContaining(String rootRole, String adminRole);
}