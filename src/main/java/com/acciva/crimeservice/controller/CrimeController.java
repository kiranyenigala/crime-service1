package com.acciva.crimeservice.controller;

import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrimeController {

    private CrimeService crimeService;

    @Autowired
    public CrimeController(final CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @RequestMapping(value = "/crime/categories", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getCrimeCategories()  {
        CrimeCategory[] crimeCategories = this.crimeService.getCrimeCategories();
        if (crimeCategories == null || crimeCategories.length == 0) {
            return new ResponseEntity<>("No Data found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(crimeCategories, HttpStatus.OK);
    }


    @RequestMapping(value = "/crimes", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getCrimes(@RequestParam("postcode") final String postCode,
                                            @RequestParam("date") final String date)  {
        CrimeAtLocation[] crimeAtLocations = this.crimeService.getCrimes(postCode, date);
        if (crimeAtLocations == null || crimeAtLocations.length == 0) {
            return new ResponseEntity<>("No Data found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(crimeAtLocations, HttpStatus.OK);
    }
}
