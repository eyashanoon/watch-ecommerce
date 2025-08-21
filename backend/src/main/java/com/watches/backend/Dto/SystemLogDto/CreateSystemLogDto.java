package com.watches.backend.Dto.SystemLogDto;

import com.watches.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateSystemLogDto {
    @NotBlank(message = "Log Action is required")
    private String action;
    @NotBlank(message = "Action performer is required")
    private User performedBy;
}
