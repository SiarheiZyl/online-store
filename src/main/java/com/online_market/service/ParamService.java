package com.online_market.service;

import com.online_market.entity.Param;

import java.util.Set;

/**
 * Service interface for ${@link Param}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface ParamService {

    Set<String> getAllAuthors();

    Set<String> getAllCountries();
}
