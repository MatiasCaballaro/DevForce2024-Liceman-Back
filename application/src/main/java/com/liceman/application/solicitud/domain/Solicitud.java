package com.liceman.application.solicitud.domain;

import com.liceman.application.solicitud.domain.enums.Status;
import com.liceman.application.usuario.domain.User;
import com.liceman.application.usuario.domain.enums.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private Integer days;

    @Enumerated(EnumType.STRING)
    private Status status;

    //TODO: Ver si hacemos List<String> del comentario, quiza hacer una clase Comentario
    @Column(name = "user_comment")
    private String userComment;

    //TODO: Ver si hacemos List<String> del comentario, quiza hacer una clase Comentario
    @Column(name = "mentor_comment")
    private String mentorComment;

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
