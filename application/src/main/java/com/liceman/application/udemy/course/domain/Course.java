package com.liceman.application.udemy.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course{
    private String _class;
    private int id;
    private String title;
    private int estimated_content_length;
    private List<String> categories;
    private int num_lectures;
    private int num_videos;
    private List<VideoUrl> promo_video_url;
    private List<String> instructors;
    private Requirements requirements;
    private WhatYouWillLearn what_you_will_learn;
    private Images images;
    private int num_quizzes;
    private boolean has_closed_caption;
    private List<String> caption_languages;
    private int estimated_content_length_video;
    private String last_update_date;
    private String xapi_activity_id;
    private String headline;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoUrl {
        private String type;
        private String label;
        private String file;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Requirements {
        private List<String> list;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WhatYouWillLearn {
        private List<String> list;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Images {
        private String size_48x27;
        private String size_50x50;
        private String size_75x75;
        private String size_96x54;
        private String size_100x100;
        private String size_125_H;
        private String size_200_H;
        private String size_240x135;
        private String size_304x171;
        private String size_480x270;
        private String size_750x422;
    }

}