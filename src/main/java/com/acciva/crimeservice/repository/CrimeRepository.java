package com.acciva.crimeservice.repository;

import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;

public interface CrimeRepository {

    CrimeCategory[] getCrimeCategories();

    CrimeAtLocation[] getCrimes(final String postCode,
                                final String date);
}
