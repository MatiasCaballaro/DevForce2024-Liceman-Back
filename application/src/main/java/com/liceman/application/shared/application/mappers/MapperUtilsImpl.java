package com.liceman.application.shared.application.mappers;

import com.liceman.application.training.domain.Comment;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.infrastructure.dto.CommentDTO;
import com.liceman.application.training.infrastructure.dto.TrainingDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperUtilsImpl implements MapperUtils {

    @Override
    public UserResponseDTO mapperToUserDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole().toString());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setArea(user.getArea());
        userResponseDTO.setHasTeams(user.getHasTeams());
        userResponseDTO.setTrainings(user.getTrainings().stream().map(this::mapperToTrainingUserResponseDTO).collect(Collectors.toList()));
        return userResponseDTO;
    }

    @Override
    public UserResponseWithoutTrainingDTO mapperToUserWithoutTrainingDTO(User user) {
        UserResponseWithoutTrainingDTO userDTO = new UserResponseWithoutTrainingDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().toString());
        userDTO.setPhone(user.getPhone());
        userDTO.setHasTeams(user.getHasTeams());
        return userDTO;
    }

    @Override
    public TrainingDTO mapperToTrainingUserResponseDTO(Training training) {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(training.getId())
                .area(training.getArea())
                .creationDate(training.getCreationDate())
                .status(training.getStatus())
                .comments(training.getComments().stream().map(this::mapperToCommentDTO).collect(Collectors.toList()))
                .days(training.getDays())
                .link(training.getLink())
                .approvedDate(training.getApprovedDate())
                .endDate(training.getEndDate())
                .userId(this.mapperToUserWithoutTrainingDTO(training.getUserId()))
                .build();
        if (training.getMentorId() != null) {
            trainingDTO.setMentorId(this.mapperToUserWithoutTrainingDTO(training.getMentorId()));
        }
        if (training.getAdminId() != null) {
            trainingDTO.setAdminId(this.mapperToUserWithoutTrainingDTO(training.getAdminId()));
        }
        return trainingDTO;
    }

    @Override
    public CommentDTO mapperToCommentDTO(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .userName(comment.getUser_id().getFirstname() + " " + comment.getUser_id().getLastname())
                .user_id(comment.getUser_id().getId())
                .message(comment.getMessage())
                .created_at(comment.getCreated_at())
                .build();
        return commentDTO;
    }
}
