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
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class AddressDaoImpl extends GenericDaoImpl<Address> implements AddressDao {

    final static Logger logger = Logger.getLogger(AddressDao.class);

    @Autowired
    SessionFactory sessionFactory;
}
