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

/**
 * Class implementing ${@link ParamDao}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class ParamDaoImpl extends GenericDaoImpl<Param> implements ParamDao {

    final static Logger logger = Logger.getLogger(ParamDao.class);

    private final SessionFactory sessionFactory;

    /**
     * Injecting constructor for SessionFactory exemplar
     */
    @Autowired
    public ParamDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saving param
     *
     * @param param param
     */
    @Override
    public void save(Param param) {
        sessionFactory.getCurrentSession().save(param);
    }

    /**
     * Getting all params
     *
     * @return list of ${@link Param}
     */
    @Override
    public List<Param> getAllParams() {
        Query query = sessionFactory.getCurrentSession().createQuery("select e from Param e");
        return query.getResultList();
    }

    /**
     * Getting all authors
     *
     * @return list of authors
     */
    @Override
    public Set<String> getAllAuthors() {

        List<Param> params = getAllParams();
        Set<String> authors = new HashSet<>();
        for (Param param : params) {
            authors.add(param.getAuthor());
        }
        return authors;
    }

    /**
     * Getting all countries
     *
     * @return list of countries
     */
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
