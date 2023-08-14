package com.liceman.application.training.domain;

import com.liceman.application.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"training_id", "user_id"})
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    Training training_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user_id;

    @NotBlank
    String message;

    LocalDateTime created_at;

    @PrePersist
    public void prePersist(){
        created_at = LocalDateTime.now();
    }
}
