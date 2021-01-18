package com.acciva.crimeservice;

import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.model.OutcomeStatus;
import com.acciva.crimeservice.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected CrimeCategory[] buildCrimeCategories() {
        CrimeCategory[] arrCrimeCategories = new CrimeCategory[2];
        arrCrimeCategories[0] = createCrimeCategory("all-crime", "All crime and ASB");
        arrCrimeCategories[1] = createCrimeCategory("burglary", "Burglary");
        return arrCrimeCategories;
    }

    private CrimeCategory createCrimeCategory(String url, String name) {
        CrimeCategory crimeCategory = new CrimeCategory();
        crimeCategory.setUrl(url);
        crimeCategory.setName(name);
        return crimeCategory;
    }

    protected CrimeAtLocation[] buildCrimesAtLocation() {
        CrimeAtLocation[] arrCrimeAtLocation = new CrimeAtLocation[1];
        arrCrimeAtLocation[0] = createCrimeAtLocation();
        return arrCrimeAtLocation;
    }

    private CrimeAtLocation createCrimeAtLocation() {
        CrimeAtLocation crimeAtLocation = new CrimeAtLocation();
        crimeAtLocation.setCategory("violent-crime");
        Result result = new Result();
        result.setLatitude("51.431655");
        result.setLongitude("0.116353");
        result.setPostcode(null);
        crimeAtLocation.setLocation(result);
        crimeAtLocation.setContext("");
        crimeAtLocation.setMonth("2018-03");
        OutcomeStatus outcomeStatus = new OutcomeStatus();
        outcomeStatus.setCategory("Investigation complete; no suspect identified");
        outcomeStatus.setDate("2018-05");
        crimeAtLocation.setOutcomeStatus(outcomeStatus);
        return crimeAtLocation;
    }
}
