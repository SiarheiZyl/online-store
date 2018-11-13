package com.online_market.service;

import com.online_market.dao.AddressDao;
import com.online_market.dao.ItemDaoImpl;
import com.online_market.entity.Address;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class implementing ${@link AddressService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    final static Logger logger = Logger.getLogger(AddressService.class);

    @Autowired
    AddressDao addressDao;

    /**
     * Saving address
     *
     * @param address address
     */
    @Override
    public void save(Address address) {

        logger.info("Starting saving address(called save(Address address))");

        addressDao.save(address);

        logger.info("Address was saved");
    }

    /**
     * Updating address
     *
     * @param address address
     */
    @Override
    public void update(Address address) {

        logger.info("Starting updating address(called saveOrUpdate(Address address))");

        addressDao.saveOrUpdate(address);

        logger.info("Address was updated");
    }

    /**
     * Getting address by id
     *
     * @param id address id
     * @return address
     */
    @Override
    public Address getById(int id) {

        logger.info("Getting address by id(called getById(int id))");

        return addressDao.getById(Address.class,id);
    }
}
