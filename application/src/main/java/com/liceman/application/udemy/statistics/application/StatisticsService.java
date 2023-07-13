package com.liceman.application.udemy.statistics.application;

import com.liceman.application.udemy.statistics.domain.ActivityCourseResult;
import com.liceman.application.udemy.statistics.domain.ActivityResult;

import java.io.IOException;
import java.util.List;

public interface StatisticsService {

    ActivityResult getUserActivity (String email) throws IOException;

    List<ActivityCourseResult> getCoursesActivity (String email) throws IOException;

}
