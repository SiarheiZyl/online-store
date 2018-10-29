package com.online_market.service;

import com.online_market.dao.AddressDao;
import com.online_market.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressDao addressDao;

    @Override
    public void save(Address address) {
        addressDao.save(address);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public Address getById(int id) {
        return addressDao.getById(id);
    }
}
