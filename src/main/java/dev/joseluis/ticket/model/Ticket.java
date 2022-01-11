package dev.joseluis.ticket.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service", nullable = false)
    private Service service;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "support")
    private User support;

    public User getSupport() {
        return support;
    }

    public void setSupport(User support) {
        this.support = support;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", service=" + service +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", status=" + status +
                ", customer=" + customer +
                ", support=" + support +
                '}';
    }
}