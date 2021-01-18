package com.acciva.crimeservice.service;

import com.acciva.crimeservice.BaseTest;
import com.acciva.crimeservice.InvalidDataException;
import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.repository.CrimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrimeServiceTest extends BaseTest {

    private CrimeService crimeService;

    private CrimeRepository crimeRepository;

    @BeforeEach
    void setUp() {
        crimeRepository = mock(CrimeRepository.class);
        crimeService = new CrimeService(crimeRepository);

        when(crimeRepository.getCrimeCategories()).thenReturn(buildCrimeCategories());
        when(crimeRepository.getCrimes(anyString(), anyString())).thenReturn(buildCrimesAtLocation());
    }

    @Test
    void getCrimeCategoriesAsExpected() {
        CrimeCategory[] crimeCategories = crimeService.getCrimeCategories();
        assertEquals(2, crimeCategories.length);
        assertEquals("all-crime", crimeCategories[0].getUrl());
        assertEquals("All crime and ASB", crimeCategories[0].getName());
        assertEquals("burglary", crimeCategories[1].getUrl());
        assertEquals("Burglary", crimeCategories[1].getName());
    }

    @Test
    void getCrimesAsExpected() {
        CrimeAtLocation[] crimesAtLocation = crimeService.getCrimes("DA14 4NB", "2018-03");

        assertEquals(1, crimesAtLocation.length);
        assertEquals("violent-crime", crimesAtLocation[0].getCategory());
        assertEquals("2018-05", crimesAtLocation[0].getOutcomeStatus().getDate());
        assertEquals("Investigation complete; no suspect identified",
                crimesAtLocation[0].getOutcomeStatus().getCategory());
    }


    @Test
    void expectInvalidPostCodeError() {
        assertThrows(InvalidDataException.class,
                () -> crimeService.getCrimes(null, null));
    }

    @Test
    void expectInvalidPostCodeErrorWhenEmpty() {
        assertThrows(InvalidDataException.class,
                () -> crimeService.getCrimes("", ""));
    }

    @Test
    void expectInvalidPostCodeErrorWhenPostCodeInvalid() {
        assertThrows(InvalidDataException.class,
                () -> crimeService.getCrimes("DA1449nn", "2018-08"));
    }

    @Test
    void expectInvalidPostCodeErrorWhenDateInvalid() {
        assertThrows(InvalidDataException.class,
                () -> crimeService.getCrimes("DA1449nn", "2018-33"));
    }

}