package com.online_market.service;

import com.online_market.dao.ParamDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    final static Logger logger = Logger.getLogger(ParamService.class);

    @Autowired
    public ParamDao paramDao;

    @Override
    public Set<String> getAllAuthors() {

        logger.info("Getting all authors(called getAllAuthors())");

        return paramDao.getAllAuthors();
    }

    @Override
    public Set<String> getAllCountries() {

        logger.info("Getting all countries(called getAllCountries())");

        return paramDao.getAllCountries();
    }
}
