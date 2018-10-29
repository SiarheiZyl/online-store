package com.online_market.dao;

import com.online_market.entity.Address;

public interface AddressDao {

    void save(Address address);

    void update(Address address);

    Address getById(int id);
}
