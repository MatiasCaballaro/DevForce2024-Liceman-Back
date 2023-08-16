package com.liceman.application.training.domain;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    private String title;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private Integer days;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "training_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Comment> comments = new ArrayList<>();

    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    private User mentorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User adminId;

}
