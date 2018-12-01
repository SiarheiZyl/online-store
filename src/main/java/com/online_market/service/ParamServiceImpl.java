package com.online_market.service;

import com.online_market.dao.ParamDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Class implementing ${@link ParamService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    final static Logger logger = Logger.getLogger(ParamService.class);


    private final ParamDao paramDao;

    /**
     * Injecting constructor
     * @param paramDao param DAO
     */
    @Autowired
    public ParamServiceImpl(ParamDao paramDao) {
        this.paramDao = paramDao;
    }

    /**
     * Getting all authors
     *
     * @return list of authors
     */
    @Override
    public Set<String> getAllAuthors() {

        logger.info("Getting all authors(called getAllAuthors())");

        return paramDao.getAllAuthors();
    }


    /**
     * Getting all countries
     *
     * @return list of countries
     */
    @Override
    public Set<String> getAllCountries() {

        logger.info("Getting all countries(called getAllCountries())");

        return paramDao.getAllCountries();
    }
}
