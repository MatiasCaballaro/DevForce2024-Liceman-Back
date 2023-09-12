package com.liceman.application.udemy.statistics.application;

import com.liceman.application.udemy.statistics.domain.Activity;
import com.liceman.application.udemy.statistics.domain.ActivityCourse;
import com.liceman.application.udemy.statistics.domain.ActivityCourseResult;
import com.liceman.application.udemy.statistics.domain.ActivityResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

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
    public ActivityResult getUserActivity(String email) throws IOException {
        if (!mockActivated) {
            logger.info("Fetching Udemy user activity for email: {}", email);
            return callUdemyActivity(email);

        }
        logger.info("Fetching mock user activity for email: {}", email);
        return callMockActivity();
    }

    private ActivityResult callMockActivity() {
        String mockedActivityUrl = mockedEndpoint + "/udemy/activity";
        logger.info("Calling mock activity URL: {}", mockedActivityUrl);
        ResponseEntity<Activity> response = restTemplate.exchange(
                mockedActivityUrl,
                HttpMethod.GET,
                null,
                Activity.class
        );
        return Objects.requireNonNull(response.getBody()).getResults().stream().findFirst().orElse(null);
    }

    private ActivityResult callUdemyActivity(String email) throws IOException {
        try {
            ResponseEntity<Activity> response = restTemplate.exchange(
                    getActivityCoursesUrlWith(email),
                    HttpMethod.GET,
                    new HttpEntity<>(addUdemyAuthHeaders()),
                    Activity.class
            );
            return Objects.requireNonNull(response.getBody()).getResults().stream().findFirst().orElse(null);
        } catch (Exception e) {
            logger.error("Error while fetching user activity: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    private String getActivityUrlWith(String email) {
        return udemyEndpoint
                + "/api-2.0/organizations/"
                + accountId
                + "/analytics/user-activity/?fields[aggregate_organization_user_activity]=@all&user_email="
                + email;
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


    @Override
    public List<ActivityCourseResult> getCoursesActivity(String email) throws IOException {
        if (!mockActivated) {
            logger.info("Fetching Udemy courses activity for email: {}", email);
            return callUdemyActivityCourses(email);
        }
        logger.info("Fetching mock courses activity for email: {}", email);
        return callMockActivityCourses();
    }

    private List<ActivityCourseResult> callMockActivityCourses() {
        String mockedActivityCourseUrl = mockedEndpoint + "/udemy/activity-course";
        logger.info("Calling mock activity courses URL: {}", mockedActivityCourseUrl);
        ResponseEntity<ActivityCourse> response = restTemplate.exchange(
                mockedActivityCourseUrl,
                HttpMethod.GET,
                null,
                ActivityCourse.class
        );
        return Objects.requireNonNull(response.getBody()).getResults();
    }

    private List<ActivityCourseResult> callUdemyActivityCourses(String email) throws IOException {
        try {
            ResponseEntity<ActivityCourse> response = restTemplate.exchange(
                getActivityCoursesUrlWith(email),
                HttpMethod.GET,
                new HttpEntity<>(addUdemyAuthHeaders()),
                ActivityCourse.class
            );
            return Objects.requireNonNull(response.getBody()).getResults();
        } catch (Exception e) {
            logger.error("Error while fetching courses activity: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    private String getActivityCoursesUrlWith(String email) {
        return udemyEndpoint
                + "/api-2.0/organizations/"
                + accountId
                + "/analytics/user-course-activity/?fields[aggregate_organization_user_course_activity]=@all&page=1&page_size=20&user_email="
                + email;
    }

}
