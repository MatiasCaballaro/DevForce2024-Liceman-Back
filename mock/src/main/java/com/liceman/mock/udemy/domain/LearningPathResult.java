package com.liceman.mock.udemy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathResult {
    private String _class;
    private long id;
    private String url;
    private String title;
    private String description;
    private List<Editor> editors;
    private LocalDateTime created;
    private LocalDateTime last_modified;
    private int estimated_content_length;
    private int number_of_content_items;
    private List<LearningPathSection> sections;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Editor {
        private String _class;
        private String display_name;
        private String email;
        private String lms_user_id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LearningPathSection {
        private String _class;
        private long id;
        private String title;
        private String description;
        private int order;
        private List<Item> items;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Item {
            private long id;
            private String title;
            private String url;
            private String type;
            private double duration;
            private int number_of_items;
            private int order;
            private Thumbnail thumbnail;
            private String resource_url;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Thumbnail {
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
    }
}