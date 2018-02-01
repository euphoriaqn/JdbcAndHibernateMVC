package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.Utils.FetchConfig;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T, ID> {

    void save(T t);

    T getById(ID id, FetchConfig fetchConfig) throws SQLException;

    void remove(ID id);

    List<T> getAll();

}
