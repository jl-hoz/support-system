package dev.joseluis.ticket.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "content", nullable = false, length = 3000)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "message_id", nullable = false)
    private Ticket message;

    public Ticket getMessage() {
        return message;
    }

    public void setMessage(Ticket message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", created=" + created +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", message=" + message +
                '}';
    }
}