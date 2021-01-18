package com.acciva.crimeservice.controller;

import com.acciva.crimeservice.BaseTest;
import com.acciva.crimeservice.CrimeServiceApplication;
import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.service.CrimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        CrimeServiceApplication.class
})
class CrimeControllerTest extends BaseTest {

    @Mock
    private CrimeService crimeService;

    @InjectMocks
    private CrimeController crimeController;

    private MockMvc mockMvc;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(crimeController).build();

        when(crimeService.getCrimeCategories()).thenReturn(buildCrimeCategories());
        when(crimeService.getCrimes(anyString(), anyString())).thenReturn(buildCrimesAtLocation());
    }

    @Test
    void getCrimeCategoriesAsExpected() throws Exception {

        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/crime/categories"))
                .andReturn();

        CrimeCategory[] crimeCategories = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        CrimeCategory[].class);

        assertEquals(2, crimeCategories.length);
        assertEquals("all-crime", crimeCategories[0].getUrl());
        assertEquals("All crime and ASB", crimeCategories[0].getName());
        assertEquals("burglary", crimeCategories[1].getUrl());
        assertEquals("Burglary", crimeCategories[1].getName());
    }

    @Test
    void getCrimesAsExpected() throws Exception {

        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/crimes?postcode=da15 7ln&date=2018-03"))
                .andReturn();

        CrimeAtLocation[] crimesAtLocation = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        CrimeAtLocation[].class);
        assertEquals(1, crimesAtLocation.length);
        assertEquals("violent-crime", crimesAtLocation[0].getCategory());
        assertEquals("2018-05", crimesAtLocation[0].getOutcomeStatus().getDate());
        assertEquals("Investigation complete; no suspect identified",
                crimesAtLocation[0].getOutcomeStatus().getCategory());
    }
}