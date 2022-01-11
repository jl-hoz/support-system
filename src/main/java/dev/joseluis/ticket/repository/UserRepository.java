package dev.joseluis.ticket.repository;

import dev.joseluis.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User SET active = ?2 WHERE email = ?1")
    @Transactional
    void toggleActive(String email, Boolean isActive);

    List<User> findAllByRoleNotContaining(String role);
    List<User> findAllByRoleNotContainingAndRoleNotContaining(String rootRole, String adminRole);

    List<User> findAllByRoleContainingAndActiveIsTrue(String supportRole);
}