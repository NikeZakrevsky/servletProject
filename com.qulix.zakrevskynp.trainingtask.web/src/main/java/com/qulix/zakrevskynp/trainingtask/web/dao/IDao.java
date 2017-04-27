package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.BaseDAOEntity;

/**
 * Interface with dao methods
 *
 * @param <T> class of the entity
 * @author Q-NZA
 */
public interface IDao<T extends BaseDAOEntity> {
    List<T> getAll();
    void remove(int id);
    void add(T entity);
    T getById(int id);
    void update(T entity);
}
