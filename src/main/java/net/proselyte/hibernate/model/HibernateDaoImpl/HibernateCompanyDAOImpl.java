package net.proselyte.hibernate.model.HibernateDaoImpl;

import net.proselyte.hibernate.Utils.HibConnectionUtil;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.dao.CompanyDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateCompanyDAOImpl implements CompanyDAO{

    private SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();


    public void save(Company company){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(company);
        transaction.commit();
        session.close();
        System.out.println("Company object was saved");
    }


    public Company getById(Long id, FetchConfig fetchConfig) {
        Session session = this.sessionFactory.openSession();
        Company company = session.get(Company.class, id);
        if (fetchConfig.isProjects())
            company.getProjects().iterator();
        session.close();
        return company;
    }

    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Company company = session.get(Company.class, id);
        session.delete(company);
        transaction.commit();
        session.close();
    }

    public List<Company> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Company");
        List<Company> result = query.list();
        session.close();
        return result;
    }

    public void addProjectsToComp(Long compId, Long projId) {
        Session session = sessionFactory.openSession();
        Transaction transaction2 = session.beginTransaction();
        Company company = session.get(Company.class, compId);
        ProjectDAO projectDAO = new HibernateProjectDAOImpl();
//        Project project = projectDAO.getById(projId);
//        company.getProjects().add(project);
//        session.merge(project);
        transaction2.commit();
        session.close();
    }
}
