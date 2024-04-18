package com.project.opendota.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.opendota.dto.ExternalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExternalApiResponse getMatchInfo(Long matchId) {
        try {
            String url = "https://api.opendota.com/api/matches/" + matchId;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            Long match_id = jsonResponse.get("match_id").asLong();
            Long duration = jsonResponse.get("duration").asLong();
            int radiantScore = jsonResponse.get("radiant_score").asInt();
            int direScore = jsonResponse.get("dire_score").asInt();
            String patch = jsonResponse.get("patch").asText();
            String region = jsonResponse.get("region").asText();

            return new ExternalApiResponse(match_id, duration, radiantScore, direScore, patch, region);

        } catch (Exception exception) {
            return null;
        }
    }
}