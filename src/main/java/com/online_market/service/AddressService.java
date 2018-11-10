package com.online_market.service;

import com.online_market.entity.Address;

/**
 * Service interface for ${@link Address}
 * @author Siarhei
 * @version 1.0
 */
public interface AddressService {

    void save(Address address);

    void update(Address address);

    Address getById(int id);
}
