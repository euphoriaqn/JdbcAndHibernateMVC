package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.model.POJO.Company;

import java.sql.SQLException;

public interface CompanyDAO extends GenericDAO<Company, Long>{
    void addProjectsToComp(Long compId, Long projId) throws SQLException;
}
