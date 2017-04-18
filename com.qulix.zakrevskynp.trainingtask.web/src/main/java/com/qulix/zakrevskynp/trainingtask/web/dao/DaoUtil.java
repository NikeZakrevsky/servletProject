package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ZakrevskyNP on 14.04.2017.
 * @author Q-NZA
 */
public interface DaoUtil<T> {
    T resultSetAsObject(ResultSet resultSet) throws SQLException;
    List<T> resultSetToList(ResultSet rs) throws SQLException;
    int setPreparedStatement(PreparedStatement preparedStatement, T entity) throws SQLException;
}
