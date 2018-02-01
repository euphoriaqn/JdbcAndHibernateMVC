package net.proselyte.hibernate.model.JdbcDaoImpl;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.Utils.JDBCConnectionUtil;
import net.proselyte.hibernate.dao.CompanyDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Company;
import net.proselyte.hibernate.model.POJO.Project;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcCompanyDAOImpl implements CompanyDAO{
        private PreparedStatement updateSt;
        private PreparedStatement selectSt;
        private PreparedStatement insertSt;
        private PreparedStatement deleteSt;
        private PreparedStatement getProjects;
        private PreparedStatement addProjToComp;
        private Connection connection;

        public JdbcCompanyDAOImpl() {
            try {
                connection = JDBCConnectionUtil.getInstance().getConnection();
                selectSt = connection.prepareStatement("SELECT * FROM companies WHERE ID=?");
                updateSt = connection.prepareStatement("UPDATE companies SET CompanyName=? WHERE id=?");
                insertSt = connection.prepareStatement("INSERT INTO companies (CompanyName) VALUE (?)");
                deleteSt = connection.prepareStatement("DELETE FROM companies WHERE id=?");
                getProjects = connection.prepareStatement("SELECT projects.id, projects.ProjectCost, projects.ProjectName " +
                        "FROM projects " +
                        "WHERE projects.Comp_id = ?");
                addProjToComp = connection.prepareStatement("update projects set Comp_id = ? where id = ?");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Company getById(Long id, FetchConfig fetchConfig) throws SQLException {

            selectSt.setLong(1, id);
            ResultSet resultSet = selectSt.executeQuery();
            Company company = new Company();
            while (resultSet.next()) {
                Long companyrId = resultSet.getLong("id");
                String firstName = resultSet.getString("CompanyName");
                company.withId(companyrId)
                        .withName(firstName);
            }
            if (company.getId() == null) {
                throw new IllegalArgumentException();
            }
            if (fetchConfig.isProjects()) {
                getProjects.setLong(1, id);
                ResultSet resultSetSk = getProjects.executeQuery();
                Set<Project> projects = new HashSet<Project>();
                while (resultSetSk.next()) {
                    Long prId = resultSetSk.getLong("id");
                    BigDecimal cost = resultSetSk.getBigDecimal("ProjectCost");
                    String prName = resultSetSk.getString("ProjectName");
                    Project project = new Project();
                    project.withId(prId).withCost(cost).withName(prName);
                    projects.add(project);
                }
                company.setProjects(projects);
            }

            return company;
        }

        public List<Company> getAll() {
            String selectAllQuery = "SELECT * FROM companies";
            List<Company> companies = new ArrayList<Company>();
            ResultSet rs = null;
            try {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery(selectAllQuery);

                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String companyName = rs.getString("CompanyName");
                    Company company = new Company();
                    company.withId(id)
                            .withName(companyName);
                    companies.add(company);
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

            return companies;
        }

        public void save(Company company) {

            if (company.getId() != null)
                update(company);
            else {
                String name = company.getCompanyName();
                try {
                    insertSt.setString(1, name);
                    insertSt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void update(Company company) {
            try {
                updateSt.setString(1, company.getCompanyName());
                updateSt.setLong(2, company.getId());
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

    public void addProjectsToComp(Long compId, Long projId) throws SQLException {
        ProjectDAO projectDAO = new JdbcProjectDAOImpl();
        Project project = projectDAO.getById(projId, new FetchConfig());

        Company company = getById(compId, new FetchConfig());

        if (project.getId() == null | company.getId() == null) {
            throw new IllegalArgumentException();
        }
        addProjToComp.setLong(1, compId);
        addProjToComp.setLong(2, projId);
        addProjToComp.executeUpdate();

    }
}
