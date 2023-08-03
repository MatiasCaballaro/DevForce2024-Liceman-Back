package com.liceman.application.udemy.course.application;

import com.liceman.application.udemy.course.domain.Course;
import com.liceman.application.udemy.course.domain.Courses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    @Value("${mock.udemy}")
    boolean mockActivated;

    @Value("${mock.udemy.endpoint}")
    String mockedEndpoint;

    @Value("${udemy.endpoint}")
    String udemyEndpoint;

    @Value("${udemy.account.id}")
    String accountId;

    @Value("${udemy.client.id}")
    String clientId;

    @Value("${udemy.client.secret}")
    String secret;

    private final RestTemplate restTemplate;

    @Override
    public Courses getCourses() throws IOException {
        if (!mockActivated) {
            return callUdemyCourses();
        }
        return callMockCourses();
    }

    private Courses callMockCourses() {
        String mockedActivityCourseUrl = mockedEndpoint + "/udemy/courses";
        ResponseEntity<Courses> response = restTemplate.exchange(
                mockedActivityCourseUrl,
                HttpMethod.GET,
                null,
                Courses.class
        );
        return Objects.requireNonNull(response.getBody());
    }

    private HttpHeaders addUdemyAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = clientId + ":" + secret;
        byte[] credentialsBytes = credentials.getBytes();
        byte[] base64CredentialsBytes = Base64.encodeBase64(credentialsBytes);
        String base64Credentials = new String(base64CredentialsBytes);
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }

    private Courses callUdemyCourses() throws IOException {
        try {
            ResponseEntity<Courses> response = restTemplate.exchange(
                getCoursesURL(),
                HttpMethod.GET,
                new HttpEntity<>(addUdemyAuthHeaders()),
                Courses.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private String getCoursesURL() {
        return udemyEndpoint +
                "/api-2.0/organizations/" +
                accountId +
                "/courses/list/" +
                "?page=1&page_size=20" +
                "&fields[course]=title,headline,estimated_content_length,categories,num_lectures," +
                "num_videos,promo_video_url,instructors,requirements,what_you_will_learn,images,num_quizzes," +
                "has_closed_caption,caption_languages," +
                "estimated_content_length_video,last_update_date,xapi_activity_id";
    }

    @Override
    public Course getCourseById(int id) throws IOException {
        if (!mockActivated) {
            return callUdemyCourseById(id);
        }
        return callMockCourseById(id);
    }

    private Course callMockCourseById(int id) {
        String mockedActivityCourseUrl = mockedEndpoint + "/udemy/courses/" + id;
        ResponseEntity<Course> response = restTemplate.exchange(
                mockedActivityCourseUrl,
                HttpMethod.GET,
                null,
                Course.class
        );
        return Objects.requireNonNull(response.getBody());
    }

    private Course callUdemyCourseById(int id) throws IOException {
        try {
            ResponseEntity<Course> response =restTemplate.exchange(
                getCourseURLWithId(id),
                HttpMethod.GET,
                new HttpEntity<>(addUdemyAuthHeaders()),
                Course.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private String getCourseURLWithId(int id) {
        return udemyEndpoint
                + "/api-2.0/organizations/"
                + accountId
                + "/courses/list/"
                + id
                + "?fields[course]=title,headline,estimated_content_length,categories,num_lectures," +
                "num_videos,promo_video_url,instructors,requirements,what_you_will_learn,images,num_quizzes," +
                "has_closed_caption,caption_languages," +
                "estimated_content_length_video,last_update_date,xapi_activity_id";
    }
}
