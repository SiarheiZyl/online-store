package com.online_market.service;

import com.online_market.dao.ParamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    @Autowired
    public ParamDao paramDao;

    @Override
    public Set<String> getAllAuthors() {
        return paramDao.getAllAuthors();
    }

    @Override
    public Set<String> getAllCountries() {
        return paramDao.getAllCountries();
    }
}
