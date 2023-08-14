package com.liceman.mock.shared.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreatorFromJsonServiceImpl implements CreatorFromJsonService{
    @Override
    public <T> ResponseEntity<T> createFromJson(String json, Class<T> targetClass){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        T result = null;
        try {
            result = objectMapper.readValue(json, targetClass);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(result);
    }
}
