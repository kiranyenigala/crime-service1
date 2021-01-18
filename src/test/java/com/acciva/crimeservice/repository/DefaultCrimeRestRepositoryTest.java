package com.acciva.crimeservice.repository;

import com.acciva.crimeservice.BaseTest;
import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.PostCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

class DefaultCrimeRestRepositoryTest extends BaseTest {

    private DefaultCrimeRestRepository crimeRestRepository;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        crimeRestRepository = new DefaultCrimeRestRepository(restTemplate);
    }

    @Test
    void validateNoDataFoundExceptionWhenGetCrimesInvoked() {

        PostCode postCode = mock(PostCode.class);
        when(postCode.getLatitude()).thenReturn("11");
        when(postCode.getLongitude()).thenReturn("114");
        ResponseEntity<PostCode> responseEntity = new ResponseEntity<>(postCode, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(PostCode.class)))
                .thenReturn(responseEntity);

        when(restTemplate.exchange(anyString(), eq(GET), any(), eq(CrimeAtLocation[].class)))
                .thenThrow(new RestClientException("exception"));

        assertThrows(ResponseStatusException.class,
                () -> crimeRestRepository.getCrimes("da14 4nb", "2018-03"));
    }

    @Test
    void getCrimes() {
    }
}