package com.liceman.application.udemy.course.application;

import com.liceman.application.udemy.course.domain.Course;
import com.liceman.application.udemy.course.domain.Courses;

import java.io.IOException;

public interface CourseService {

    Courses getCourses() throws IOException;

    Course getCourseById(int id) throws IOException;
}
