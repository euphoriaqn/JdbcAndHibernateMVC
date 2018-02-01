package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.model.POJO.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends GenericDAO<Customer, Long> {
    void addProjToCust(Long custId, Long projId) throws SQLException;
}
