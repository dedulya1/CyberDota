package com.project.opendota.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalApiResponse {
    private Long match_id;
    private Long duration;
    private int radiantScore;
    private int direScore;
    private String patch;
    private String region;

    public ExternalApiResponse(Long match_id, Long duration, int radiantScore, int direScore, String patch, String region) {
        this.match_id = match_id;
        this.duration = duration;
        this.radiantScore = radiantScore;
        this.direScore = direScore;
        this.patch = patch;
        this.region = region;
    }
}