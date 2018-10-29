package com.online_market.dao;


import com.online_market.entity.Address;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Address address) {
        sessionFactory.getCurrentSession().persist(address);
    }

    @Override
    public void update(Address address) {
        if(getById(address.getAddressId()) == null)
            save(address);
        else
            sessionFactory.getCurrentSession().update(address);
    }

    @Override
    public Address getById(int id) {

        Session session = sessionFactory.openSession();
        Address address = session.get(Address.class, id);
        Hibernate.initialize(address);
        session.close();

        return address;
    }
}
