package com.online_market.dao;


import com.online_market.entity.Address;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class implementing ${@link AddressDao}
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class AddressDaoImpl implements AddressDao {

    final static Logger logger = Logger.getLogger(AddressDao.class);

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Saving address
     * @param address address
     */
    @Override
    public void save(Address address) {
        sessionFactory.getCurrentSession().persist(address);
    }


    /**
     * Updating address
     * @param address address
     */
    @Override
    public void update(Address address) {
        if(getById(address.getAddressId()) == null)
            save(address);
        else
            sessionFactory.getCurrentSession().update(address);
    }

    /**
     * Getting address by id
     * @param id id
     * @return address
     */
    @Override
    public Address getById(int id) {

        Session session = sessionFactory.openSession();
        Address address = session.get(Address.class, id);
        Hibernate.initialize(address);
        session.close();

        return address;
    }
}
