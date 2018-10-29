package com.online_market.service;

import com.online_market.entity.Address;

public interface AddressService {

    void save(Address address);

    void update(Address address);

    Address getById(int id);
}
