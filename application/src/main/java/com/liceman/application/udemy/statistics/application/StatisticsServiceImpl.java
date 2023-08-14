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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

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


    @Override
    public ActivityResult getUserActivity(String email) throws IOException {
        if (!mockActivated) {
            return callUdemyActivity(email);
        }
        return callMockActivity();
    }

    private ActivityResult callMockActivity() {
        String mockedActivityUrl = mockedEndpoint + "/udemy/activity";
        ResponseEntity<Activity> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                .exchange(mockedActivityUrl, HttpMethod.GET, null, Activity.class);
        return Objects.requireNonNull(response.getBody()).getResults().stream().findFirst().orElse(null);
    }

    private ActivityResult callUdemyActivity(String email) throws IOException {
        try {
            ResponseEntity<Activity> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                    .exchange(
                            getActivityUrlWith(email),
                            HttpMethod.GET,
                            new HttpEntity<>(addUdemyAuthHeaders()),
                            Activity.class);

            return Objects.requireNonNull(response.getBody()).getResults().stream().findFirst().orElse(null);
        } catch (Exception e) {
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
            return callUdemyActivityCourses(email);
        }
        return callMockActivityCourses();
    }

    private List<ActivityCourseResult> callMockActivityCourses() {
        String mockedActivityCourseUrl = mockedEndpoint + "/udemy/activity-course";
        ResponseEntity<ActivityCourse> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                .exchange(mockedActivityCourseUrl, HttpMethod.GET, null, ActivityCourse.class);
        return Objects.requireNonNull(response.getBody()).getResults();
    }

    private List<ActivityCourseResult> callUdemyActivityCourses(String email) throws IOException {
        try {
            ResponseEntity<ActivityCourse> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                    .exchange(
                            getActivityCoursesUrlWith(email),
                            HttpMethod.GET,
                            new HttpEntity<>(addUdemyAuthHeaders()),
                            ActivityCourse.class);

            return Objects.requireNonNull(response.getBody()).getResults();
        } catch (Exception e) {
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
