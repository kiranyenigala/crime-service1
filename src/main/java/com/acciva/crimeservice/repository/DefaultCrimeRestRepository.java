package com.acciva.crimeservice.repository;

import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.model.PostCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpMethod.GET;

@Repository
public class DefaultCrimeRestRepository implements CrimeRepository {

    private final RestTemplate restTemplate;

    @Value("${crime.service.police.crime-categories}")
    private String crimeCategoriesServiceUrl;

    @Value("${crime.service.police.crimes-at-location}")
    private String crimesAtLocationServiceUrl;

    @Value("${crime.service.postcode.url}")
    private String postCodeServiceUrl;

    @Autowired
    public DefaultCrimeRestRepository(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CrimeCategory[] getCrimeCategories() {
        try {
            final HttpEntity requestEntity = new HttpEntity(new HttpHeaders());
            return restTemplate.exchange(crimeCategoriesServiceUrl, GET, requestEntity, CrimeCategory[].class).getBody();
        } catch (RestClientException re) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
    }


    public CrimeAtLocation[] getCrimes(final String postCode, final String date) {
        String urlComplete = buildCrimeAtLocationUrl(postCode, date);
        try {
            final HttpEntity requestEntity = new HttpEntity(new HttpHeaders());
            return restTemplate.exchange(urlComplete, GET, requestEntity, CrimeAtLocation[].class).getBody();
        } catch (RestClientException re){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
    }

    private String buildCrimeAtLocationUrl(String postCode, String date) {
        PostCode postCodeInfo = getPostCodeInfo(postCode);
        return crimesAtLocationServiceUrl
                        + "?date="
                        + date
                        + "&lat="
                        + postCodeInfo.getLatitude()
                        + "&lng="
                        + postCodeInfo.getLongitude();
    }

    private PostCode getPostCodeInfo(final String postCode) {
        return restTemplate
                .exchange(postCodeServiceUrl + postCode, GET, new HttpEntity(new HttpHeaders()), PostCode.class)
                .getBody();
    }
}
