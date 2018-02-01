package net.proselyte.hibernate.model.HibernateDaoImpl;

import net.proselyte.hibernate.Utils.HibConnectionUtil;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.dao.CustomerDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Customer;
import net.proselyte.hibernate.model.POJO.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class HibernateCustomerDAOImpl implements CustomerDAO{

    private SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();

    public void save(Customer customer){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(customer);
        transaction.commit();
        session.close();
        System.out.println("Customer object was saved");
    }


    public Customer getById(Long id, FetchConfig fetchConfig) {
        Session session = this.sessionFactory.openSession();
        Customer customer = session.get(Customer.class, id);
        if (fetchConfig.isProjects())
            customer.getProjects().iterator();
        session.close();
        return customer;
    }

    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, id);
        session.delete(customer);
        transaction.commit();
        session.close();
    }

    public List<Customer> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Customer d");
        List<Customer> result = query.list();
        session.close();
        return result;
    }

    public void addProjToCust(Long custId, Long projId) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction2 = session.beginTransaction();
        Customer customer = session.get(Customer.class, custId);
        ProjectDAO projectDAO = new HibernateProjectDAOImpl();
        Project project = projectDAO.getById(projId, new FetchConfig());
        customer.getProjects().add(project);
        session.merge(project);
        transaction2.commit();
        session.close();
    }
}
