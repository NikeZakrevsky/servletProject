package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.BaseDAOEntity;

/**
 * Interface with dao methods
 *
 * @param <T> class of the entity
 */
public interface IDao<T extends BaseDAOEntity> {
    List<T> getAll(String selectQuery, String error);
    void remove(int id, String removeQuery, String error);
    void add(T entity, String insertQuery, String error);
    T getById(int id, String getByIdQuery, String error);
    void update(T entity, int id, String updateQuery, String error);
}
