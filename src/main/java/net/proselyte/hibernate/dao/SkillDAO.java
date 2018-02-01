package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.model.POJO.Skill;

import java.sql.SQLException;

public interface SkillDAO extends GenericDAO<Skill, Long>  {
    void addSkillsToDev(Long SkillId, Long DevId) throws SQLException;
}
