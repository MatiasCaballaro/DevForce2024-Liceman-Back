package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.Training;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long>, PagingAndSortingRepository<Training, Long> {

    List<Training> findAllByUserIdIs(User user , Pageable pageable);
    List<Training> findAllByArea (Area area, Pageable pageable);

}
