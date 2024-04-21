package com.project.opendota.dto;

public class ExternalApiRequest {
  private Long id;

  public ExternalApiRequest(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}