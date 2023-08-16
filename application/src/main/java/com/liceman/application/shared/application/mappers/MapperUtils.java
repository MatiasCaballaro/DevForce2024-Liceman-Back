package com.liceman.application.shared.application.mappers;

import com.liceman.application.training.domain.Comment;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.infrastructure.dto.CommentDTO;
import com.liceman.application.training.infrastructure.dto.TrainingDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;

public interface MapperUtils {

    UserResponseDTO mapperToUserDTO(User user);

    UserResponseWithoutTrainingDTO mapperToUserWithoutTrainingDTO(User user);

    TrainingDTO mapperToTrainingUserResponseDTO (Training training);

    CommentDTO mapperToCommentDTO (Comment comment);



}
