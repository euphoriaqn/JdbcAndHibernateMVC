package net.proselyte.hibernate.dao;

import net.proselyte.hibernate.model.POJO.Developer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface DeveloperDAO extends GenericDAO<Developer, Long> {

    void addSkillToDev(Long SkillId, Long DevId) throws SQLException;
}
