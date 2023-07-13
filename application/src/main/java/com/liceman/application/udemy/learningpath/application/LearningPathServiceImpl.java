package com.liceman.application.udemy.learningpath.application;

import com.liceman.application.udemy.learningpath.domain.LearningPath;
import com.liceman.application.udemy.learningpath.domain.LearningPathResult;
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
import java.util.Objects;

@Service
public class LearningPathServiceImpl implements LearningPathService {

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
    public LearningPath getLearningPaths() throws IOException {
        if (!mockActivated) {
            return callUdemyLearningPath();
        }
        return callMockLearningPath();
    }

    private LearningPath callMockLearningPath() {
        String mockedActivityLearningPathUrl = mockedEndpoint + "/udemy/learning-paths";
        ResponseEntity<LearningPath> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                .exchange(mockedActivityLearningPathUrl, HttpMethod.GET, null, LearningPath.class);
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
            ResponseEntity<LearningPath> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                    .exchange(
                            getLearningPath(),
                            HttpMethod.GET,
                            new HttpEntity<>(addUdemyAuthHeaders()),
                            LearningPath.class);
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
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
            return callUdemyLearningPathByID(id);
        }
        return callMockLearningPathByID(id);
    }

    private LearningPathResult callMockLearningPathByID(int id) {
        String mockedActivityLearningPathUrl = mockedEndpoint + "/udemy/learning-paths/"+id;
        ResponseEntity<LearningPathResult> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                .exchange(mockedActivityLearningPathUrl, HttpMethod.GET, null, LearningPathResult.class);
        return Objects.requireNonNull(response.getBody());
    }

    private LearningPathResult callUdemyLearningPathByID(int id) throws IOException {
        try {
            ResponseEntity<LearningPathResult> response = new RestTemplate(new SimpleClientHttpRequestFactory())
                    .exchange(
                            getLearningPathURLWithId(id),
                            HttpMethod.GET,
                            new HttpEntity<>(addUdemyAuthHeaders()),
                            LearningPathResult.class);
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
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
