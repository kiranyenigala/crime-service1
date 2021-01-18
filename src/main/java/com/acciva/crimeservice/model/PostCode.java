package com.acciva.crimeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCode {

    private Result result;

    public String getLongitude() {
        return result.getLongitude();
    }

    public String getLatitude() {
        return result.getLatitude();
    }
}
