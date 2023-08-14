package com.liceman.mock.shared.application;

import org.springframework.http.ResponseEntity;

public interface CreatorFromJsonService {

    <T> ResponseEntity<T> createFromJson(String json, Class<T> targetClass);
}
