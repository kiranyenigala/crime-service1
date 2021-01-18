package com.acciva.crimeservice.service;

import com.acciva.crimeservice.InvalidDataException;
import com.acciva.crimeservice.model.CrimeAtLocation;
import com.acciva.crimeservice.model.CrimeCategory;
import com.acciva.crimeservice.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CrimeService {

    private final String POST_CODE_REGEX = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";

    private final String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])$";

    private CrimeRepository crimeRepository;

    @Autowired
    public CrimeService(final CrimeRepository crimeRepository) {
        this.crimeRepository = crimeRepository;
    }

    public CrimeCategory[] getCrimeCategories() {
        return this.crimeRepository.getCrimeCategories();
    }


    public CrimeAtLocation[] getCrimes(final String postCode,
                                     final String date) {
        boolean isEmpty = ObjectUtils.isEmpty(postCode) || ObjectUtils.isEmpty(date);

        if (isEmpty) {
            throw new InvalidDataException(String.format("Invalid post code or date %s %s ", postCode, date));
        }


        if ( !postCode.toUpperCase().matches(POST_CODE_REGEX) || !date.matches(DATE_REGEX)) {
            throw new InvalidDataException(String.format("Invalid post code or date %s %s ", postCode, date));
        }

        return crimeRepository.getCrimes(postCode, date);
    }

}
