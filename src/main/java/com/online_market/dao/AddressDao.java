package com.online_market.dao;

import com.online_market.entity.Address;

/**
 * Dao interface for ${@link Address}
 * @author Siarhei
 * @version 1.0
 */
public interface AddressDao {

    void save(Address address);

    void update(Address address);

    Address getById(int id);
}
