package com.project.opendota.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalApiResponse {
  private Long id;
  private Long duration;
  private int radiantScore;
  private int direScore;
  private String patch;
  private String region;

  public ExternalApiResponse(Long id, Long duration, int radiantScore, int direScore,
                             String patch, String region) {
    this.id = id;
    this.duration = duration;
    this.radiantScore = radiantScore;
    this.direScore = direScore;
    this.patch = patch;
    this.region = region;
  }
}