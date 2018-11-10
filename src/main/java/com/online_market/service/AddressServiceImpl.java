package com.online_market.service;

import com.online_market.dao.AddressDao;
import com.online_market.dao.ItemDaoImpl;
import com.online_market.entity.Address;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    final static Logger logger = Logger.getLogger(AddressService.class);

    @Autowired
    AddressDao addressDao;

    @Override
    public void save(Address address) {

        logger.info("Starting saving address(called save(Address address))");

        addressDao.save(address);

        logger.info("Address was saved");
    }

    @Override
    public void update(Address address) {

        logger.info("Starting updating address(called update(Address address))");

        addressDao.update(address);

        logger.info("Address was updated");
    }

    @Override
    public Address getById(int id) {

        logger.info("Getting address by id(called getById(int id))");

        return addressDao.getById(id);
    }
}
