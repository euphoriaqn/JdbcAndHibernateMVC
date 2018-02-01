package net.proselyte.hibernate.model.JdbcDaoImpl;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.Utils.JDBCConnectionUtil;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.POJO.Developer;
import net.proselyte.hibernate.model.POJO.Skill;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcDeveloperDAOImpl implements DeveloperDAO {
    private PreparedStatement updateSt;
    private PreparedStatement selectSt;
    private PreparedStatement insertSt;
    private PreparedStatement deleteSt;
    private PreparedStatement getSkills;
    private PreparedStatement addSkillToDev;
    private Connection connection;

    public JdbcDeveloperDAOImpl() {
        try {
            connection = JDBCConnectionUtil.getInstance().getConnection();
            //       statement = connection.createStatement();
            selectSt = connection.prepareStatement("SELECT * FROM DEVELOPERS WHERE ID=?");
            updateSt = connection.prepareStatement("UPDATE developers SET first_name=?, last_name=?, salary=? WHERE id=?");
            insertSt = connection.prepareStatement("INSERT INTO DEVELOPERS (FIRST_NAME, LAST_NAME, salary) VALUES(?, ?, ?)");
            deleteSt = connection.prepareStatement("DELETE FROM DEVELOPERS WHERE id=?");
            getSkills = connection.prepareStatement("SELECT skill.ID, skill.SkillName " +
                    "FROM skill " +
                    "JOIN " +
                    "developers_skills " +
                    "ON skill.ID = developers_skills.skill_id " +
                    "JOIN " +
                    "developers " +
                    "ON developers_skills.developer_id = developers.id AND developers.id = ?");
            addSkillToDev = connection.prepareStatement("insert into developers_skills (skill_id, developer_id) values (?,?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer getById(Long id, FetchConfig fetchConfig) throws SQLException {

        selectSt.setLong(1, id);
        ResultSet resultSet = selectSt.executeQuery();
        Developer developer = new Developer();

        while (resultSet.next()) {
            Long developerId = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            BigDecimal salary = resultSet.getBigDecimal("salary");

            developer.withFirstName(firstName)
                    .withLastName(lastName)
                    .withSalary(salary)
                    .withID(developerId);
        }
        if (developer.getId() == null) {
            throw new IllegalArgumentException();
        }
            if (fetchConfig.isSkills()) {
            getSkills.setLong(1, id);
            ResultSet resultSetSk = getSkills.executeQuery();
            Set<Skill> skills = new HashSet<Skill>();
                while (resultSetSk.next()) {
                    Long skillID = resultSetSk.getLong("ID");
                    String skillName = resultSetSk.getString("SkillName");
                    Skill skill = new Skill();
                    skill.setId(skillID);
                    skill.setSkillName(skillName);
                    skills.add(skill);
                }
                developer.setSkills(skills);
            }
        return developer;
    }

    public List<Developer> getAll() {
        String selectAllQuery = "SELECT * FROM DEVELOPERS";
        List<Developer> developers = new ArrayList<Developer>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                BigDecimal salary = rs.getBigDecimal("salary");
                Long id = rs.getLong("id");
                Developer developer = new Developer();
                developer.setFirstName(firstName);
                developer.setLastName(lastName);
                developer.setSalary(salary);
                developer.setId(id);

                developers.add(developer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
        }

        return developers;
    }

    public void save(Developer developer) {

        if (developer.getId() != 0)
            update(developer);
        else {
            String firstName = developer.getFirstName();
            String lastName = developer.getLastName();
            BigDecimal salary = developer.getSalary();

            try {
                insertSt.setString(1, firstName);
                insertSt.setString(2, lastName);
                insertSt.setBigDecimal(3, salary);
                insertSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Developer developer) {
        try {
            updateSt.setString(1, developer.getFirstName());
            updateSt.setString(2, developer.getLastName());
            updateSt.setBigDecimal(3, developer.getSalary());
            updateSt.setLong(4, developer.getId());
            updateSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(Long id) {
        try {
            deleteSt.setLong(1, id);
            deleteSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSkillToDev(Long devId, Long skillId) throws SQLException {
        SkillDAO skillDAO = new JdbcSkillDAOImpl();
        Skill skill = skillDAO.getById(skillId, new FetchConfig());
        Developer developer = getById(devId, new FetchConfig());

        if (skill.getSkillName() == null | developer.getFirstName() == null) {
            throw new IllegalArgumentException();
        }
        addSkillToDev.setLong(1, skillId);
        addSkillToDev.setLong(2, devId);
        addSkillToDev.executeUpdate();
    }
}
