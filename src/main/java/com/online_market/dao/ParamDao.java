package com.online_market.dao;

import com.online_market.entity.Param;

import java.util.List;
import java.util.Set;

public interface ParamDao {

    Set<String> getAllAuthors();

    Set<String> getAllCountries();

    List<Param> getAllParams();
}
