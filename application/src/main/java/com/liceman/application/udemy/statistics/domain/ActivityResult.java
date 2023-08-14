package com.liceman.application.udemy.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResult {
    String user_name;
    String user_surname;
    String user_email;
    String user_role;
    String user_joined_date;
    String user_external_id;
    boolean user_is_deactivated;
    int num_new_enrolled_courses;
    int num_new_assigned_courses;
    int num_new_started_courses;
    int num_completed_courses;
    int num_completed_lectures;
    int num_completed_quizzes;
    Double num_video_consumed_minutes;
    int num_web_visited_days;
    String last_date_visit;
}
