package com.liceman.application.udemy.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCourseResult {
	private Integer user_id;
	private String user_name;
	private String user_surname;
	private String user_email;
	private String user_role;
	private Integer user_external_id;
	private Integer course_id;
	private String course_title;
	private String course_category;
	private Double course_duration;
	private Double completion_ratio;
	private Double num_video_consumed_minutes;
	private String course_enroll_date;
	private String course_start_date;
	private String course_completion_date;
	private String course_first_completion_date;
	private String course_last_accessed_date;
	private Boolean is_assigned;
	private Integer assigned_by;
	private Boolean user_is_deactivated;
	private Integer lms_user_id;
}