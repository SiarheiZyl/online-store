package com.online_market.dao;

import com.online_market.entity.Address;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class implementing ${@link AddressDao}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class AddressDaoImpl extends GenericDaoImpl<Address> implements AddressDao {

    /**
     * SessionFactory exemplar through which we get
     * sessions and perform database operations
     */
    private final SessionFactory sessionFactory;

    /**
     * Injecting constructor for SessionFactory exemplar
     */
    @Autowired
    public AddressDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
