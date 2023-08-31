package com.liceman.mock.udemy.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liceman.mock.shared.application.CreatorFromJsonService;
import com.liceman.mock.udemy.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/udemy")
@Tag(name = "Users")
@RequiredArgsConstructor
public class udemyMockController {

    private final CreatorFromJsonService creatorFromJsonService;
    @Operation(
            description = "test Mock Activity"
    )
    @GetMapping("/activity")
    public ResponseEntity<Activity> mockActivity() {

        ActivityResult activityResult = ActivityResult.builder()
                .user_name("Test")
                .user_surname("Mock")
                .user_email("testmock@mail.com")
                .user_role("student")
                .user_joined_date("2021-12-29")
                .user_external_id(null)
                .user_is_deactivated(false)
                .num_new_enrolled_courses(11)
                .num_new_assigned_courses(0)
                .num_new_started_courses(9)
                .num_completed_courses(8)
                .num_completed_lectures(120)
                .num_completed_quizzes(15)
                .num_video_consumed_minutes(1234.30)
                .num_web_visited_days(77)
                .last_date_visit("2023-05-30")
                .build();
        return ResponseEntity.ok().body(Activity.builder()
                .count(1)
                .next(null)
                .results(Collections.singletonList(activityResult))
                .build());
    }

    @Operation(
            description = "test Mock Activity Courses"
    )
    @GetMapping("/activity-course")
    public ResponseEntity<ActivityCourse> mockActivityCourse() {
        ActivityCourseResult activityCourseResult1 = ActivityCourseResult.builder()
                .user_id(1234)
                .user_name("test")
                .user_surname("mock")
                .user_email("test@mock.com")
                .user_role("student")
                .user_external_id(null)
                .course_id(3078492)
                .course_title("Máster Completo en Java de cero a experto 2023 (+127 hrs)")
                .course_category("Programming Languages")
                .course_duration(7613.0)
                .completion_ratio(100.0)
                .num_video_consumed_minutes(8220.5)
                .course_enroll_date("2022-05-02T06:50:54Z")
                .course_start_date("2022-05-02T06:52:22Z")
                .course_completion_date("2023-04-19T18:57:37Z")
                .course_first_completion_date("2023-04-19T18:57:37Z")
                .course_last_accessed_date("2023-05-10T15:28:51Z")
                .is_assigned(false)
                .assigned_by(null)
                .user_is_deactivated(false)
                .lms_user_id(null)
                .build();

        ActivityCourseResult activityCourseResult2 = ActivityCourseResult.builder()
                .user_id(1234)
                .user_name("test")
                .user_surname("mock")
                .user_email("test@mock.com")
                .user_role("student")
                .user_external_id(null)
                .course_id(3662358)
                .course_title("Angular: De cero a experto - Edición 2023")
                .course_category("Web Development")
                .course_duration(2525.0)
                .completion_ratio(10.0)
                .num_video_consumed_minutes(350.5)
                .course_enroll_date("2022-05-11T14:43:18Z")
                .course_start_date("2022-05-11T14:43:18Z")
                .course_completion_date(null)
                .course_first_completion_date(null)
                .course_last_accessed_date("2022-07-05T12:31:14Z")
                .is_assigned(false)
                .assigned_by(null)
                .user_is_deactivated(false)
                .lms_user_id(null)
                .build();

        ActivityCourseResult activityCourseResult3 = ActivityCourseResult.builder()
                .user_id(1234)
                .user_name("test")
                .user_surname("mock")
                .user_email("test@mock.com")
                .user_role("student")
                .user_external_id(null)
                .course_id(3611296)
                .course_title("Clean Code")
                .course_category("Software Engineering")
                .course_duration(400.0)
                .completion_ratio(100.0)
                .num_video_consumed_minutes(413.25)
                .course_enroll_date("2023-04-21T19:30:41Z")
                .course_start_date("2023-04-21T19:31:26Z")
                .course_completion_date("2023-04-23T04:57:01Z")
                .course_first_completion_date("2023-04-23T04:57:01Z")
                .course_last_accessed_date("2023-04-23T04:57:00Z")
                .is_assigned(false)
                .assigned_by(null)
                .user_is_deactivated(false)
                .lms_user_id(null)
                .build();


        return ResponseEntity.ok().body(ActivityCourse.builder()
                .count(1)
                .next(null)
                .results(Arrays.asList(activityCourseResult1, activityCourseResult2, activityCourseResult3))
                .build());
    }


    @Operation(
            description = "test Mock Activity Courses"
    )
    @GetMapping("/courses")
    public ResponseEntity<Courses> mockCourses() {
        Course course = Course.builder()
                ._class("course")
                .id(533682)
                .title("Java Programming Masterclass updated to Java 17")
                .estimated_content_length(7330)
                .categories(Arrays.asList("Programming Languages", "Mobile Development"))
                .num_lectures(649)
                .num_videos(709)
                .promo_video_url(Arrays.asList(
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("720")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD_720p.mp4?secure=HbtplEWOsMWhcmBb5AgNLA%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("480")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD_480.mp4?secure=_sJG7jREifHwjDXc02eAPA%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("360")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD.mp4?secure=bqFomQfStqHnVZX5JohXbg%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("144")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/Web_144.mp4?secure=zI9KXY7te6IMS7xDQPnI6Q%3D%3D%2C1689113218")
                                .build()
                ))
                .instructors(Arrays.asList("Tim Buchalka", "Tim Buchalka's Learn Programming Academy"))
                .requirements(Course.Requirements.builder()
                        .list(Arrays.asList(
                                "A computer with either Windows, Mac or Linux to install all the free software and tools needed to build your new apps (I provide specific videos on installations for each platform).",
                                "A strong work ethic, willingness to learn, and plenty of excitement about the awesome new programs you’re about to build.",
                                "Nothing else! It’s just you, your computer and your hunger to get started today."
                        ))
                        .build()
                )
                .what_you_will_learn(Course.WhatYouWillLearn.builder()
                        .list((Arrays.asList(
                                "Learn the core Java skills needed to apply for Java developer positions in just 14 hours.",
                                "Be able to sit for and pass the Oracle Java Certificate exam if you choose.",
                                "Be able to demonstrate your understanding of Java to future employers.",
                                "Learn industry \"best practices\" in Java software development from a professional Java developer who has worked in the language for 18 years.",
                                "Acquire essential java basics for transitioning to the Spring Framework, Java EE, Android development and more.",
                                "Obtain proficiency in Java 8 and Java 11."
                        )))
                        .build()
                )
                .images(Course.Images.builder()
                        .size_48x27("https://img-b.udemycdn.com/course/48x27/533682_c10c_4.jpg")
                        .size_50x50("https://img-c.udemycdn.com/course/50x50/533682_c10c_4.jpg")
                        .size_75x75("https://img-c.udemycdn.com/course/75x75/533682_c10c_4.jpg")
                        .size_96x54("https://img-c.udemycdn.com/course/96x54/533682_c10c_4.jpg")
                        .size_100x100("https://img-c.udemycdn.com/course/100x100/533682_c10c_4.jpg")
                        .size_125_H("https://img-c.udemycdn.com/course/125_H/533682_c10c_4.jpg")
                        .size_200_H("https://img-c.udemycdn.com/course/200_H/533682_c10c_4.jpg")
                        .size_240x135("https://img-c.udemycdn.com/course/240x135/533682_c10c_4.jpg")
                        .size_304x171("https://img-c.udemycdn.com/course/304x171/533682_c10c_4.jpg")
                        .size_480x270("https://img-c.udemycdn.com/course/480x270/533682_c10c_4.jpg")
                        .size_750x422("https://img-c.udemycdn.com/course/750x422/533682_c10c_4.jpg")
                        .build()
                )
                .num_quizzes(57)
                .has_closed_caption(true)
                .caption_languages(Arrays.asList(
                        "English", "Bulgarian [Auto]", "Czech [Auto]", "Danish [Auto]", "Dutch [Auto]", "Estonian [Auto]",
                        "Finnish [Auto]", "French [Auto]", "German [Auto]", "Greek [Auto]", "Hungarian [Auto]", "Indonesian [Auto]",
                        "Italian [Auto]", "Korean [Auto]", "Latvian [Auto]", "Lithuanian [Auto]", "Portuguese [Auto]",
                        "Romanian [Auto]", "Simplified Chinese [Auto]", "Slovak [Auto]", "Spanish [Auto]", "Swedish [Auto]",
                        "Ukrainian [Auto]", "Vietnamese [Auto]"
                ))
                .estimated_content_length_video(7303)
                .last_update_date("2023-06-27")
                .xapi_activity_id("https://www.udemy.com/course/533682")
                .headline("Learn Java In This Course And Become a Computer Programmer. Obtain valuable Core Java Skills And Java Certification")
                .build();
        return ResponseEntity.ok().body(Courses.builder()
                .count(1)
                .next(null)
                .previous(null)
                .results(Collections.singletonList(course))
                .build());
    }

    @Operation(
            description = "test Mock Activity Course by id"
    )
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> mockCourseById(@PathVariable int id) {
        Course course = Course.builder()
                ._class("course")
                .id(533682)
                .title("Java Programming Masterclass updated to Java 17")
                .estimated_content_length(7330)
                .categories(Arrays.asList("Programming Languages", "Mobile Development"))
                .num_lectures(649)
                .num_videos(709)
                .promo_video_url(Arrays.asList(
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("720")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD_720p.mp4?secure=HbtplEWOsMWhcmBb5AgNLA%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("480")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD_480.mp4?secure=_sJG7jREifHwjDXc02eAPA%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("360")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/WebHD.mp4?secure=bqFomQfStqHnVZX5JohXbg%3D%3D%2C1689113218")
                                .build(),
                        Course.VideoUrl.builder()
                                .type("video/mp4")
                                .label("144")
                                .file("https://mp4-b.udemycdn.com/2021-09-29_18-20-46-dc771277139b8516a8fa9b5013a45a38/2/Web_144.mp4?secure=zI9KXY7te6IMS7xDQPnI6Q%3D%3D%2C1689113218")
                                .build()
                ))
                .instructors(Arrays.asList("Tim Buchalka", "Tim Buchalka's Learn Programming Academy"))
                .requirements(Course.Requirements.builder()
                        .list(Arrays.asList(
                                "A computer with either Windows, Mac or Linux to install all the free software and tools needed to build your new apps (I provide specific videos on installations for each platform).",
                                "A strong work ethic, willingness to learn, and plenty of excitement about the awesome new programs you’re about to build.",
                                "Nothing else! It’s just you, your computer and your hunger to get started today."
                        ))
                        .build()
                )
                .what_you_will_learn(Course.WhatYouWillLearn.builder()
                        .list((Arrays.asList(
                                "Learn the core Java skills needed to apply for Java developer positions in just 14 hours.",
                                "Be able to sit for and pass the Oracle Java Certificate exam if you choose.",
                                "Be able to demonstrate your understanding of Java to future employers.",
                                "Learn industry \"best practices\" in Java software development from a professional Java developer who has worked in the language for 18 years.",
                                "Acquire essential java basics for transitioning to the Spring Framework, Java EE, Android development and more.",
                                "Obtain proficiency in Java 8 and Java 11."
                        )))
                        .build()
                )
                .images(Course.Images.builder()
                        .size_48x27("https://img-b.udemycdn.com/course/48x27/533682_c10c_4.jpg")
                        .size_50x50("https://img-c.udemycdn.com/course/50x50/533682_c10c_4.jpg")
                        .size_75x75("https://img-c.udemycdn.com/course/75x75/533682_c10c_4.jpg")
                        .size_96x54("https://img-c.udemycdn.com/course/96x54/533682_c10c_4.jpg")
                        .size_100x100("https://img-c.udemycdn.com/course/100x100/533682_c10c_4.jpg")
                        .size_125_H("https://img-c.udemycdn.com/course/125_H/533682_c10c_4.jpg")
                        .size_200_H("https://img-c.udemycdn.com/course/200_H/533682_c10c_4.jpg")
                        .size_240x135("https://img-c.udemycdn.com/course/240x135/533682_c10c_4.jpg")
                        .size_304x171("https://img-c.udemycdn.com/course/304x171/533682_c10c_4.jpg")
                        .size_480x270("https://img-c.udemycdn.com/course/480x270/533682_c10c_4.jpg")
                        .size_750x422("https://img-c.udemycdn.com/course/750x422/533682_c10c_4.jpg")
                        .build()
                )
                .num_quizzes(57)
                .has_closed_caption(true)
                .caption_languages(Arrays.asList(
                        "English", "Bulgarian [Auto]", "Czech [Auto]", "Danish [Auto]", "Dutch [Auto]", "Estonian [Auto]",
                        "Finnish [Auto]", "French [Auto]", "German [Auto]", "Greek [Auto]", "Hungarian [Auto]", "Indonesian [Auto]",
                        "Italian [Auto]", "Korean [Auto]", "Latvian [Auto]", "Lithuanian [Auto]", "Portuguese [Auto]",
                        "Romanian [Auto]", "Simplified Chinese [Auto]", "Slovak [Auto]", "Spanish [Auto]", "Swedish [Auto]",
                        "Ukrainian [Auto]", "Vietnamese [Auto]"
                ))
                .estimated_content_length_video(7303)
                .last_update_date("2023-06-27")
                .xapi_activity_id("https://www.udemy.com/course/533682")
                .headline("Learn Java In This Course And Become a Computer Programmer. Obtain valuable Core Java Skills And Java Certification")
                .build();
        return ResponseEntity.ok().body(course);

    }

    @Operation(
            description = "test Mock Learning Paths"
    )
    @GetMapping("/learning-paths")
    public ResponseEntity<LearningPath> mockLearningPaths() {
        String json = "{\n" +
                    "    \"_class\": \"learning_path\",\n" +
                    "    \"id\": 2903050,\n" +
                    "    \"url\": \"https://gire.udemy.com/learning-paths/2903050\",\n" +
                    "    \"title\": \"#DevForce - Arquitect Training for Devs\",\n" +
                    "    \"description\": \"Summarising topics related to servers, infrastructure, deploy for developers\",\n" +
                    "    \"editors\": [\n" +
                    "        {\n" +
                    "            \"_class\": \"user\",\n" +
                    "            \"display_name\": \"Javier Ottina\",\n" +
                    "            \"email\": \"Javier.Ottina@gire.com\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"created\": \"2022-04-22T17:14:38Z\",\n" +
                    "    \"last_modified\": \"2022-04-22T18:09:07Z\",\n" +
                    "    \"estimated_content_length\": 5224,\n" +
                    "    \"number_of_content_items\": 7,\n" +
                    "    \"sections\": [\n" +
                    "        {\n" +
                    "            \"_class\": \"learning_path_section\",\n" +
                    "            \"id\": 3541196,\n" +
                    "            \"items\": [\n" +
                    "                {\n" +
                    "                    \"id\": 11825430,\n" +
                    "                    \"title\": \"Linux Shell Scripting: A Project-Based Approach to Learning\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/linux-shell-scripting-projects/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 736.0,\n" +
                    "                    \"number_of_items\": 49,\n" +
                    "                    \"order\": 0,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1349660_ecfb_4.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1349660_ecfb_4.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1349660/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11825540,\n" +
                    "                    \"title\": \"Apache Tomcat Server from Beginners to Advanced\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/apache-tomcat-for-beginners-and-advanced/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 489.0,\n" +
                    "                    \"number_of_items\": 63,\n" +
                    "                    \"order\": 1,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/1183250_c82c_27.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1183250_c82c_27.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1183250/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11825554,\n" +
                    "                    \"title\": \"Docker & Kubernetes: The Practical Guide [2023 Edition]\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/docker-kubernetes-the-practical-guide/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 1420.0,\n" +
                    "                    \"number_of_items\": 269,\n" +
                    "                    \"order\": 2,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/3490000_d298_2.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-b.udemycdn.com/course/50x50/3490000_d298_2.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/3490000_d298_2.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-b.udemycdn.com/course/96x54/3490000_d298_2.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/3490000_d298_2.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/3490000_d298_2.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/3490000_d298_2.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-b.udemycdn.com/course/240x135/3490000_d298_2.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/3490000_d298_2.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/3490000_d298_2.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/3490000_d298_2.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/3490000/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11825504,\n" +
                    "                    \"title\": \"Project in DevOps: Jenkins CI/CD for Kubernetes Deployments\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/devops-project-jenkins-cicd-for-kubernetes-deployments/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 249.0,\n" +
                    "                    \"number_of_items\": 30,\n" +
                    "                    \"order\": 3,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-b.udemycdn.com/course/125_H/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-b.udemycdn.com/course/240x135/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/3328722_02ae_8.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/3328722_02ae_8.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/3328722/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11825584,\n" +
                    "                    \"title\": \"Devops Tools and AWS for Java Microservice Developers\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/devops-tools-and-aws-for-java-microservice-developers/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 1223.0,\n" +
                    "                    \"number_of_items\": 308,\n" +
                    "                    \"order\": 4,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-b.udemycdn.com/course/125_H/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/2295003_9b45_5.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/2295003_9b45_5.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/2295003/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11825602,\n" +
                    "                    \"title\": \"Openshift 4 desde cero\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/openshift-4-desde-cero/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 881.0,\n" +
                    "                    \"number_of_items\": 118,\n" +
                    "                    \"order\": 5,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1481476_5cde_3.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1481476_5cde_3.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1481476/\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 11830916,\n" +
                    "                    \"title\": \"Complete Ethical Hacking & Penetration Testing for Web Apps\",\n" +
                    "                    \"url\": \"https://gire.udemy.com/course/complete-ethical-hacking-penetration-testing-for-web-apps/\",\n" +
                    "                    \"type\": \"course\",\n" +
                    "                    \"duration\": 226.0,\n" +
                    "                    \"number_of_items\": 31,\n" +
                    "                    \"order\": 6,\n" +
                    "                    \"thumbnail\": {\n" +
                    "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1826160_3155_7.jpg\",\n" +
                    "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1826160_3155_7.jpg\",\n" +
                    "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/1826160_3155_7.jpg\",\n" +
                    "                        \"size_96x54\": \"https://img-b.udemycdn.com/course/96x54/1826160_3155_7.jpg\",\n" +
                    "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/1826160_3155_7.jpg\",\n" +
                    "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1826160_3155_7.jpg\",\n" +
                    "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1826160_3155_7.jpg\",\n" +
                    "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1826160_3155_7.jpg\",\n" +
                    "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1826160_3155_7.jpg\",\n" +
                    "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1826160_3155_7.jpg\",\n" +
                    "                        \"size_750x422\": \"https://img-b.udemycdn.com/course/750x422/1826160_3155_7.jpg\"\n" +
                    "                    },\n" +
                    "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1826160/\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            LearningPathResult learningPathResult = null;
            try {
                learningPathResult = objectMapper.readValue(json, LearningPathResult.class);
                System.out.println(learningPathResult);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            LearningPath learningPath = LearningPath.builder()
                    .count(1)
                    .next(null)
                    .previous(null)
                    .results(Arrays.asList(learningPathResult)).build();
            return ResponseEntity.ok().body(learningPath);

    }

    @Operation(
            description = "test Mock Learning Path by id"
    )
    @GetMapping("/learning-paths/{id}")
    public ResponseEntity<LearningPathResult> mockLearningPathById(@PathVariable int id) {
        String json = "{\n" +
                "    \"_class\": \"learning_path\",\n" +
                "    \"id\": 2903050,\n" +
                "    \"url\": \"https://gire.udemy.com/learning-paths/2903050\",\n" +
                "    \"title\": \"#DevForce - Arquitect Training for Devs\",\n" +
                "    \"description\": \"Summarising topics related to servers, infrastructure, deploy for developers\",\n" +
                "    \"editors\": [\n" +
                "        {\n" +
                "            \"_class\": \"user\",\n" +
                "            \"display_name\": \"Javier Ottina\",\n" +
                "            \"email\": \"Javier.Ottina@gire.com\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"created\": \"2022-04-22T17:14:38Z\",\n" +
                "    \"last_modified\": \"2022-04-22T18:09:07Z\",\n" +
                "    \"estimated_content_length\": 5224,\n" +
                "    \"number_of_content_items\": 7,\n" +
                "    \"sections\": [\n" +
                "        {\n" +
                "            \"_class\": \"learning_path_section\",\n" +
                "            \"id\": 3541196,\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"id\": 11825430,\n" +
                "                    \"title\": \"Linux Shell Scripting: A Project-Based Approach to Learning\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/linux-shell-scripting-projects/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 736.0,\n" +
                "                    \"number_of_items\": 49,\n" +
                "                    \"order\": 0,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1349660_ecfb_4.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1349660_ecfb_4.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1349660/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11825540,\n" +
                "                    \"title\": \"Apache Tomcat Server from Beginners to Advanced\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/apache-tomcat-for-beginners-and-advanced/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 489.0,\n" +
                "                    \"number_of_items\": 63,\n" +
                "                    \"order\": 1,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1183250_c82c_27.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1183250_c82c_27.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1183250_c82c_27.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1183250_c82c_27.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1183250_c82c_27.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1183250_c82c_27.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1183250_c82c_27.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1183250_c82c_27.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1183250_c82c_27.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/1183250_c82c_27.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1183250_c82c_27.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1183250/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11825554,\n" +
                "                    \"title\": \"Docker & Kubernetes: The Practical Guide [2023 Edition]\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/docker-kubernetes-the-practical-guide/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 1420.0,\n" +
                "                    \"number_of_items\": 269,\n" +
                "                    \"order\": 2,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/3490000_d298_2.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-b.udemycdn.com/course/50x50/3490000_d298_2.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/3490000_d298_2.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-b.udemycdn.com/course/96x54/3490000_d298_2.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/3490000_d298_2.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/3490000_d298_2.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/3490000_d298_2.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-b.udemycdn.com/course/240x135/3490000_d298_2.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/3490000_d298_2.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/3490000_d298_2.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/3490000_d298_2.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/3490000/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11825504,\n" +
                "                    \"title\": \"Project in DevOps: Jenkins CI/CD for Kubernetes Deployments\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/devops-project-jenkins-cicd-for-kubernetes-deployments/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 249.0,\n" +
                "                    \"number_of_items\": 30,\n" +
                "                    \"order\": 3,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/3328722_02ae_8.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/3328722_02ae_8.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/3328722_02ae_8.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/3328722_02ae_8.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/3328722_02ae_8.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-b.udemycdn.com/course/125_H/3328722_02ae_8.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/3328722_02ae_8.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-b.udemycdn.com/course/240x135/3328722_02ae_8.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/3328722_02ae_8.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/3328722_02ae_8.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/3328722_02ae_8.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/3328722/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11825584,\n" +
                "                    \"title\": \"Devops Tools and AWS for Java Microservice Developers\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/devops-tools-and-aws-for-java-microservice-developers/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 1223.0,\n" +
                "                    \"number_of_items\": 308,\n" +
                "                    \"order\": 4,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/2295003_9b45_5.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/2295003_9b45_5.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/2295003_9b45_5.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/2295003_9b45_5.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/2295003_9b45_5.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-b.udemycdn.com/course/125_H/2295003_9b45_5.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/2295003_9b45_5.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/2295003_9b45_5.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/2295003_9b45_5.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-c.udemycdn.com/course/480x270/2295003_9b45_5.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/2295003_9b45_5.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/2295003/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11825602,\n" +
                "                    \"title\": \"Openshift 4 desde cero\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/openshift-4-desde-cero/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 881.0,\n" +
                "                    \"number_of_items\": 118,\n" +
                "                    \"order\": 5,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1481476_5cde_3.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1481476_5cde_3.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-b.udemycdn.com/course/75x75/1481476_5cde_3.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-c.udemycdn.com/course/96x54/1481476_5cde_3.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-c.udemycdn.com/course/100x100/1481476_5cde_3.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1481476_5cde_3.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1481476_5cde_3.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1481476_5cde_3.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1481476_5cde_3.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1481476_5cde_3.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-c.udemycdn.com/course/750x422/1481476_5cde_3.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1481476/\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11830916,\n" +
                "                    \"title\": \"Complete Ethical Hacking & Penetration Testing for Web Apps\",\n" +
                "                    \"url\": \"https://gire.udemy.com/course/complete-ethical-hacking-penetration-testing-for-web-apps/\",\n" +
                "                    \"type\": \"course\",\n" +
                "                    \"duration\": 226.0,\n" +
                "                    \"number_of_items\": 31,\n" +
                "                    \"order\": 6,\n" +
                "                    \"thumbnail\": {\n" +
                "                        \"size_48x27\": \"https://img-c.udemycdn.com/course/48x27/1826160_3155_7.jpg\",\n" +
                "                        \"size_50x50\": \"https://img-c.udemycdn.com/course/50x50/1826160_3155_7.jpg\",\n" +
                "                        \"size_75x75\": \"https://img-c.udemycdn.com/course/75x75/1826160_3155_7.jpg\",\n" +
                "                        \"size_96x54\": \"https://img-b.udemycdn.com/course/96x54/1826160_3155_7.jpg\",\n" +
                "                        \"size_100x100\": \"https://img-b.udemycdn.com/course/100x100/1826160_3155_7.jpg\",\n" +
                "                        \"size_125_H\": \"https://img-c.udemycdn.com/course/125_H/1826160_3155_7.jpg\",\n" +
                "                        \"size_200_H\": \"https://img-c.udemycdn.com/course/200_H/1826160_3155_7.jpg\",\n" +
                "                        \"size_240x135\": \"https://img-c.udemycdn.com/course/240x135/1826160_3155_7.jpg\",\n" +
                "                        \"size_304x171\": \"https://img-c.udemycdn.com/course/304x171/1826160_3155_7.jpg\",\n" +
                "                        \"size_480x270\": \"https://img-b.udemycdn.com/course/480x270/1826160_3155_7.jpg\",\n" +
                "                        \"size_750x422\": \"https://img-b.udemycdn.com/course/750x422/1826160_3155_7.jpg\"\n" +
                "                    },\n" +
                "                    \"resource_url\": \"https://gire.udemy.com/api-2.0/organizations/139338/courses/list/1826160/\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        return creatorFromJsonService.createFromJson(json, LearningPathResult.class);

    }
}
