package com.online_market.dao;

import com.online_market.entity.Param;

import java.util.List;
import java.util.Set;

/**
 * Dao interface for ${@link Param}
 * @author Siarhei
 * @version 1.0
 */
public interface ParamDao {

    void save(Param param);

    Set<String> getAllAuthors();

    Set<String> getAllCountries();

    List<Param> getAllParams();
}
