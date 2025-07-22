package com.watches.backend.Dto.SystemLogDto;

import com.watches.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CreateSystemLogDto {
    @NotBlank(message = "Log Action is required")
    private String action;
    @NotBlank(message = "Action performer is required")
    private User performedBy;

    public CreateSystemLogDto(String action, User performedBy) {
        this.action = action;
        this.performedBy = performedBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }
}
