package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
}
