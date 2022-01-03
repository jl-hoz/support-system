package dev.joseluis.ticket.repository;

import dev.joseluis.ticket.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Modifying
    @Query("UPDATE Service SET active = ?2 WHERE name = ?1")
    @Transactional
    void toggleActive(String name, Boolean active);

    Optional<Service> findServiceByName(String name);
}
