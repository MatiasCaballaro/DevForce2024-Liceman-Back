package com.liceman.application.udemy.learningpath.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPath {
    int count;
    String next;
    String previous;
    List<LearningPathResult> results;
}
