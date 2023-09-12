package com.liceman.application.udemy.learningpath.application;

import com.liceman.application.udemy.course.application.CourseServiceImpl;
import com.liceman.application.udemy.learningpath.domain.LearningPath;
import com.liceman.application.udemy.learningpath.domain.LearningPathResult;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LearningPathServiceImpl implements LearningPathService {
    private static final Logger logger = LoggerFactory.getLogger(LearningPathServiceImpl.class);
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
    public LearningPath getLearningPaths() throws IOException {
        if (!mockActivated) {
            logger.info("Fetching Udemy learning paths");
            return callUdemyLearningPath();
        }
        logger.info("Fetching mock learning paths");
        return callMockLearningPath();
    }

    private LearningPath callMockLearningPath() {
        String mockedActivityLearningPathUrl = mockedEndpoint + "/udemy/learning-paths";
        logger.info("Calling mock learning paths URL: {}", mockedActivityLearningPathUrl);
        ResponseEntity<LearningPath> response = restTemplate.exchange(
                mockedActivityLearningPathUrl,
                HttpMethod.GET,
                null,
                LearningPath.class
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

    private LearningPath callUdemyLearningPath() throws IOException {
        try {
            ResponseEntity<LearningPath> response = restTemplate.exchange(
                getLearningPath(),
                HttpMethod.GET,
                new HttpEntity<>(addUdemyAuthHeaders()),
                LearningPath.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            logger.error("Error while fetching learning paths: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    private String getLearningPath() {
        return udemyEndpoint
                + "/api-2.0/organizations/"
                + accountId
                + "/learning-paths/list" +
                "?fields[learning_path]=@default,-is_pro_path" +
                "&fields[user]=display_name,email" +
                "&fields[learning_path_section]=items";
    }

    @Override
    public LearningPathResult getLearningPathById(int id) throws IOException {
        if (!mockActivated) {
            logger.info("Fetching Udemy learning paths");
            return callUdemyLearningPathByID(id);
        }
        logger.info("Fetching mock learning paths");
        return callMockLearningPathByID(id);
    }

    private LearningPathResult callMockLearningPathByID(int id) {
        String mockedActivityLearningPathUrl = mockedEndpoint + "/udemy/learning-paths/"+id;
        logger.info("Calling mock learning path URL with ID: {}", mockedActivityLearningPathUrl);
        ResponseEntity<LearningPathResult> response = restTemplate.exchange(
                mockedActivityLearningPathUrl,
                HttpMethod.GET,
                null,
                LearningPathResult.class
        );
        return Objects.requireNonNull(response.getBody());
    }

    private LearningPathResult callUdemyLearningPathByID(int id) throws IOException {
        try {

            ResponseEntity<LearningPathResult> response = restTemplate.exchange(
                    getLearningPathURLWithId(id),
                    HttpMethod.GET,
                    new HttpEntity<>(addUdemyAuthHeaders()),
                    LearningPathResult.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            logger.error("Error while fetching learning path with ID {}: {}", id, e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    private String getLearningPathURLWithId(int id) {
        return udemyEndpoint
                + "/api-2.0/organizations/"
                + accountId
                + "/learning-paths/list/" +
                id + "?fields[learning_path]=@default,-is_pro_path" +
                "&fields[user]=display_name,email" +
                "&fields[learning_path_section]=items";
    }
}
