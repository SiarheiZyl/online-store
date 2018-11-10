package com.online_market.dao;

import com.online_market.entity.Param;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ParamDaoImpl implements ParamDao {

    final static Logger logger = Logger.getLogger(ParamDao.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Param> getAllParams() {
        Query query = sessionFactory.getCurrentSession().createQuery("select e from Param e");
        return  query.getResultList();
    }

    @Override
    public Set<String> getAllAuthors() {

        List<Param> params = getAllParams();
        Set<String> authors = new HashSet<>();
        for (Param param : params) {
            authors.add(param.getAuthor());
        }
        return authors;
    }

    @Override
    public Set<String> getAllCountries() {

        List<Param> params = getAllParams();
        Set<String> countries = new HashSet<>();
        for (Param param : params) {
            countries.add(param.getCountry());
        }
        return countries;
    }
}
