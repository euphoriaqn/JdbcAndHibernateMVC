package net.proselyte.hibernate.model.JdbcDaoImpl;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.Utils.JDBCConnectionUtil;
import net.proselyte.hibernate.dao.CustomerDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Company;
import net.proselyte.hibernate.model.POJO.Customer;
import net.proselyte.hibernate.model.POJO.Project;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcCustomerDAOImpl implements CustomerDAO{

    private PreparedStatement updateSt;
    private PreparedStatement selectSt;
    private PreparedStatement insertSt;
    private PreparedStatement deleteSt;
    private PreparedStatement getProjects;
    private PreparedStatement addProjToCust;
    private Connection connection;

    public JdbcCustomerDAOImpl() {
        try {
            connection = JDBCConnectionUtil.getInstance().getConnection();
            //       statement = connection.createStatement();
            selectSt = connection.prepareStatement("SELECT * FROM customers WHERE ID=?");
            updateSt = connection.prepareStatement("UPDATE customers SET first_name=?, last_name=? WHERE id=?");
            insertSt = connection.prepareStatement("INSERT INTO customers (first_name, last_name) VALUES (?, ?)");
            deleteSt = connection.prepareStatement("DELETE FROM customers WHERE id=?");
            getProjects = connection.prepareStatement("SELECT * " +
                    "FROM projects " +
                    "JOIN " +
                    "customers_projects " +
                    "ON projects.id = customers_projects.project_id " +
                    "JOIN " +
                    "customers " +
                    "ON customers_projects.customer_id = customers.id AND customers.id = ?");
            addProjToCust = connection.prepareStatement("insert into customers_projects (customer_id, project_id) values (?,?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getById(Long id, FetchConfig fetchConfig) throws SQLException {

        selectSt.setLong(1, id);
        ResultSet resultSet = selectSt.executeQuery();
        Customer customer = new Customer();
        while (resultSet.next()) {
            Long iD = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String secondName = resultSet.getString("last_name");
            customer.withId(iD).withName(firstName).withSurname(secondName);
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException();
        }
        if (fetchConfig.isProjects()) {
            getProjects.setLong(1, id);
            ResultSet resultSetSk = getProjects.executeQuery();
            Set<Project> projectsCust = new HashSet<Project>();
            while (resultSetSk.next()) {
                Long prId = resultSetSk.getLong("id");
                String prName = resultSetSk.getString("ProjectName");
                BigDecimal cost = resultSetSk.getBigDecimal("ProjectCost");
                Project project = new Project();
                project.withId(prId).withCost(cost).withName(prName);
                projectsCust.add(project);
            }
            customer.setProjects(projectsCust);
        }

        return customer;
    }

    public List<Customer> getAll() {
        String selectAllQuery = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<Customer>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Customer customer = new Customer();
                customer.withId(id).withName(firstName).withSurname(lastName);
                customers.add(customer);
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

        return customers;
    }

    public void save(Customer customer) {

        if (customer.getId() != null)
            update(customer);
        else {
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            try {
                insertSt.setString(1, firstName);
                insertSt.setString(2, lastName);
                insertSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Customer customer) {
        try {
            updateSt.setString(1, customer.getFirstName());
            updateSt.setString(2, customer.getLastName());
            updateSt.setLong(3, customer.getId());
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
    public void addProjToCust(Long custId, Long projId) throws SQLException {
            ProjectDAO projectDAO = new JdbcProjectDAOImpl();
            Project project = projectDAO.getById(projId, new FetchConfig());
            Customer customer = getById(custId, new FetchConfig());
            if (project.getId() == null | customer.getId() == null) {
                throw new IllegalArgumentException();
            }
            addProjToCust.setLong(1, custId);
            addProjToCust.setLong(2, projId);
            addProjToCust.executeUpdate();
    }
}