package net.proselyte.hibernate.model.JdbcDaoImpl;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.Utils.JDBCConnectionUtil;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Developer;
import net.proselyte.hibernate.model.POJO.Project;
import net.proselyte.hibernate.model.POJO.Skill;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcProjectDAOImpl implements ProjectDAO{
    private PreparedStatement updateSt;
    private PreparedStatement selectSt;
    private PreparedStatement insertSt;
    private PreparedStatement deleteSt;
    private PreparedStatement getDevelopers;
    private PreparedStatement addDevToPro;
    private Connection connection;

    public JdbcProjectDAOImpl() {
        try {
            connection = JDBCConnectionUtil.getInstance().getConnection();
            //       statement = connection.createStatement();
            selectSt = connection.prepareStatement("SELECT * FROM projects WHERE ID=?");
            updateSt = connection.prepareStatement("UPDATE projects SET ProjectCost=?, ProjectName=? WHERE id=?");
            insertSt = connection.prepareStatement("INSERT INTO projects (ProjectCost, ProjectName) VALUES(?, ?)");
            deleteSt = connection.prepareStatement("DELETE FROM projects WHERE id=?");
            getDevelopers = connection.prepareStatement("SELECT * " +
                    "FROM developers " +
                    "JOIN " +
                    "developers_projects " +
                    "ON developers.id = developers_projects.developer_id " +
                    "JOIN " +
                    "projects " +
                    "ON developers_projects.project_id = projects.id AND projects.id = ?");
            addDevToPro = connection.prepareStatement("insert into developers_projects (project_id, developer_id) values (?,?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project getById(Long id, FetchConfig fetchConfig) throws SQLException {

        selectSt.setLong(1, id);
        ResultSet resultSet = selectSt.executeQuery();
        Project project = new Project();

        while (resultSet.next()) {
            Long projectId = resultSet.getLong("id");
            BigDecimal cost = resultSet.getBigDecimal("ProjectCost");
            String projectName = resultSet.getString("ProjectName");

            project.withId(projectId)
                    .withName(projectName)
                    .withCost(cost);
        }
        if (project.getId() == null) {
            throw new IllegalArgumentException();
        }
        if (fetchConfig.isDevelopers()) {
            getDevelopers.setLong(1, id);
            ResultSet resultSetDev = getDevelopers.executeQuery();
            Set<Developer> developers = new HashSet<Developer>();
            while (resultSetDev.next()) {
                Long devId = resultSetDev.getLong("id");
                String fName = resultSetDev.getString("first_name");
                String lName = resultSetDev.getString("last_name");
                BigDecimal salary = resultSetDev.getBigDecimal("salary");
                Developer developer = new Developer();
                developer.withID(devId)
                        .withFirstName(fName)
                        .withLastName(lName)
                        .withSalary(salary);
                developers.add(developer);
            }
            project.setDevelopers(developers);
        }
//        if (fetchConfig.isSkills()) {
//            getSkills.setLong(1, id);
//            ResultSet resultSetSk = getSkills.executeQuery();
//            Set<Skill> skills = new HashSet<Skill>();
//            while (resultSetSk.next()) {
//                Long skillID = resultSetSk.getLong("ID");
//                String skillName = resultSetSk.getString("SkillName");
//                Skill skill = new Skill();
//                skill.setId(skillID);
//                skill.setSkillName(skillName);
//                skills.add(skill);
//            }
//            developer.setSkills(skills);
//        }
        return project;
    }

    public List<Project> getAll() {
        String selectAllQuery = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<Project>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                Long projectId = rs.getLong("id");
                String lastName = rs.getString("ProjectName");
                BigDecimal cost = rs.getBigDecimal("ProjectCost");

                Project project = new Project();
                project.withId(projectId)
                        .withName(lastName)
                        .withCost(cost);

                projects.add(project);
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

        return projects;
    }

    public void save(Project project) {

        if (project.getId() != null)
            update(project);
        else {
            try {
                insertSt.setBigDecimal(1, project.getCost());
                insertSt.setString(2, project.getName());
                insertSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Project project) {
        try {
            updateSt.setBigDecimal(1, project.getCost());
            updateSt.setString(2, project.getName());
            updateSt.setLong(3, project.getId());
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

    public void addDeveloperToProj(Long projId, Long devId) throws SQLException {
        DeveloperDAO developerDAO = new JdbcDeveloperDAOImpl();
        Developer developer = developerDAO.getById(devId, new FetchConfig());

        Project project = getById(projId, new FetchConfig());

        if (developer.getId() == null | project.getId() == null) {
            throw new IllegalArgumentException();
        }
        addDevToPro.setLong(1, projId);
        addDevToPro.setLong(2, devId);
        addDevToPro.executeUpdate();
    }
}
