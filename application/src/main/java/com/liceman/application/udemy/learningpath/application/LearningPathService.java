package com.liceman.application.udemy.learningpath.application;

import com.liceman.application.udemy.learningpath.domain.LearningPath;
import com.liceman.application.udemy.learningpath.domain.LearningPathResult;

import java.io.IOException;

public interface LearningPathService {

    LearningPath getLearningPaths() throws IOException;

    LearningPathResult getLearningPathById(int id) throws IOException;
}
