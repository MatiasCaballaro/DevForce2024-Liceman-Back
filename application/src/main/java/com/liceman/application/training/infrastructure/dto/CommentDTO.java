package com.liceman.application.training.infrastructure.dto;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    String userName;
    Long user_id;
    String message;
    LocalDateTime created_at;
    @PrePersist
    public void prePersist(){
        created_at = LocalDateTime.now();
    }
}
