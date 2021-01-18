package com.acciva.crimeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrimeAtLocation {
    private String category;
    private Result location;
    private String context;
    @JsonProperty("outcome_status")
    private OutcomeStatus outcomeStatus;
    private String month;
}
