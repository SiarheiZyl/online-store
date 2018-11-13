package com.online_market.dao;

import java.util.List;

/**
 * Generic Dao interface
 *
 * @author Siarhei
 */
public interface GenericDao<T> {

    void save(T t);

    void update(T t);

    void saveOrUpdate(T t);

    T getById(final Class<T> type, int id);

    List<T> getAll(String type);
}
