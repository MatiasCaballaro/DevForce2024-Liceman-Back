package com.liceman.mock.udemy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courses {
    int count;
    String next;
    String previous;
    List<Course> results;
}
