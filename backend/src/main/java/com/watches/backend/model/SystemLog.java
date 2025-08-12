package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemLog {
    @Id
    @GeneratedValue
    private Long id;

    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User performedBy;
    private LocalDateTime performedAt;

    public SystemLog(String action, User performedBy){
        this.action = action;
        this.performedBy = performedBy;
        performedAt = LocalDateTime.now();
    }

}
