package net.proselyte.hibernate.model.HibernateDaoImpl;
import net.proselyte.hibernate.Utils.HibConnectionUtil;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Developer;
import net.proselyte.hibernate.model.POJO.Project;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class HibernateProjectDAOImpl implements ProjectDAO{
    private SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();


    public void save(Project project){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(project);
        transaction.commit();
        session.close();
        System.out.println("Project object was saved");
    }


    public Project getById(Long id, FetchConfig fetchConfig) {
        Session session = this.sessionFactory.openSession();
        Project project = session.get(Project.class, id);
        session.close();
        return project;
    }

    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Project project = session.get(Project.class, id);
        session.delete(project);
        transaction.commit();
        session.close();
    }

    public List<Project> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Project");
        List<Project> result = query.list();
        session.close();
        return result;
    }

    public void addDeveloperToProj(Long projId, Long devId) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction2 = session.beginTransaction();
        Project project = session.get(Project.class, projId);
        DeveloperDAO developerDAO = new HibernateDeveloperDAOImpl();
        Developer developer = developerDAO.getById(devId, new FetchConfig());
        project.getDevelopers().add(developer);
        session.merge(project);
        transaction2.commit();
        session.close();
    }
}
