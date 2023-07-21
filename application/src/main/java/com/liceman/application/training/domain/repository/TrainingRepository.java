package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.Training;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUserIdOrderByIdDesc(User user);

    List<Training> findAllByAreaOrderByIdDesc(Area area);

    @Query("SELECT t FROM Training t ORDER BY t.id DESC")
    List<Training> findAllOrderByIdDesc();

}
