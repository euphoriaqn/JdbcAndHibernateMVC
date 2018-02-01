package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.model.POJO.Project;

import java.sql.SQLException;

public interface ProjectDAO extends GenericDAO<Project, Long> {
    void addDeveloperToProj(Long ProjId, Long DevId) throws SQLException;
}
