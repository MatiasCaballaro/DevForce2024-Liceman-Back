package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.Training;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUserIdIs(User user);
    List<Training> findAllByArea (Area area);

}
