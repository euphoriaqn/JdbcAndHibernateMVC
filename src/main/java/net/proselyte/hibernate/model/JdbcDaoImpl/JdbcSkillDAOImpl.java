package net.proselyte.hibernate.model.JdbcDaoImpl;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.Utils.JDBCConnectionUtil;
import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.POJO.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcSkillDAOImpl implements SkillDAO{
    private PreparedStatement updateSt;
    private PreparedStatement selectSt;
    private PreparedStatement insertSt;
    private PreparedStatement deleteSt;
    private Connection connection;

    public JdbcSkillDAOImpl() {
        try {
            connection = JDBCConnectionUtil.getInstance().getConnection();
            selectSt = connection.prepareStatement("SELECT * FROM skill WHERE ID=?");
            updateSt = connection.prepareStatement("UPDATE skill SET SkillName=? WHERE id=?");
            insertSt = connection.prepareStatement("INSERT INTO skill (SkillName) VALUE (?)");
            deleteSt = connection.prepareStatement("DELETE FROM skill WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Skill getById(Long id, FetchConfig fetchConfig) throws SQLException {

        selectSt.setLong(1, id);
        ResultSet resultSet = selectSt.executeQuery();
        Skill skill = new Skill();

        while (resultSet.next()) {
            Long skillID = resultSet.getLong("ID");
            String skillName = resultSet.getString("SkillName");
            skill.withId(skillID).withName(skillName);
        }
        if (skill.getId() == null) {
            throw new IllegalArgumentException();
        }
        return skill;
    }

    public List<Skill> getAll() {
        String selectAllQuery = "SELECT * FROM skill";
        List<Skill> skills = new ArrayList<Skill>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                Long skillId = rs.getLong("ID");
                String skillName = rs.getString("SkillName");
                Skill skill = new Skill().withId(skillId).withName(skillName);
                skills.add(skill);
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

        return skills;
    }

    public void save(Skill skill) {
        if (skill.getId() != 0)
            update(skill);
        else {
            try {
                insertSt.setString(1, skill.getSkillName());
//            insertSt.setBigDecimal(3, salary);
                insertSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Skill skill) {
        try {
            updateSt.setString(1, skill.getSkillName());
            updateSt.setLong(2, skill.getId());
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

    public void addSkillsToDev(Long SkillId, Long DevId) throws SQLException {

    }
}
