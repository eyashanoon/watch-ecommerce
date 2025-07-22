package com.watches.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SystemLog {
    @Id
    @GeneratedValue
    private Long id;

    private String action;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User performedBy;
    private LocalDateTime performedAt;

    public SystemLog(String action, User performedBy, LocalDateTime performedAt) {
        this.action = action;
        this.performedBy = performedBy;
        this.performedAt = performedAt;
    }

    public SystemLog() {

    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public String getAction() {
        return action;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
