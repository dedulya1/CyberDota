package com.project.opendota.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.opendota.dto.ExternalApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ExternalApiService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ExternalApiServiceTest {
  @Autowired
  private ExternalApiService externalApiService;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo() throws RestClientException {
    // Arrange
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(null);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(1L);

    // Assert
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/1"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo2() throws RestClientException {
    // Arrange
    ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn("Body");
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(1L);

    // Assert
    verify(responseEntity).getBody();
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/1"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo3() throws RestClientException {
    // Arrange
    ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn("42");
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(1L);

    // Assert
    verify(responseEntity).getBody();
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/1"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo4() throws RestClientException {
    // Arrange
    ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn("");
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(1L);

    // Assert
    verify(responseEntity).getBody();
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/1"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo5() throws RestClientException {
    // Arrange
    ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn(1);
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(1L);

    // Assert
    verify(responseEntity).getBody();
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/1"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }

  /**
   * Method under test: {@link ExternalApiService#getMatchInfo(Long)}
   */
  @Test
  void testGetMatchInfo6() throws RestClientException {
    // Arrange
    ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn("");
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<Object>>any(),
        Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

    // Act
    ExternalApiResponse actualMatchInfo = externalApiService.getMatchInfo(2L);

    // Assert
    verify(responseEntity).getBody();
    verify(restTemplate).exchange(eq("https://api.opendota.com/api/matches/2"),
        isA(HttpMethod.class),
        isA(HttpEntity.class), isA(Class.class), isA(Object[].class));
    assertNull(actualMatchInfo);
  }
}
