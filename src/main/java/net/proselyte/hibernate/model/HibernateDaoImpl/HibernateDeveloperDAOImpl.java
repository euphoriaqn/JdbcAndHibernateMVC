package net.proselyte.hibernate.model.HibernateDaoImpl;

import net.proselyte.hibernate.Utils.HibConnectionUtil;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.POJO.Developer;
import net.proselyte.hibernate.model.POJO.Skill;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class HibernateDeveloperDAOImpl  implements DeveloperDAO {

    private SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();


    public HibernateDeveloperDAOImpl() {
    }

    public void save(Developer developer){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(developer);
        transaction.commit();
        session.close();
        System.out.println("Developer object was saved");
    }


    public Developer getById(Long id, FetchConfig fetchConfig) {
        Session session = this.sessionFactory.openSession();
        Developer developer = session.get(Developer.class, id);
        if (fetchConfig.isSkills())
            developer.getSkills().iterator();
        if (fetchConfig.isProjects())
            developer.getProjects().iterator();
        session.close();
        return developer;
    }

    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Developer developer = session.get(Developer.class, id);
        session.delete(developer);
        transaction.commit();
        session.close();
    }

    public List<Developer> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Developer d");
        List<Developer> result = query.list();
        session.close();
        return result;
    }

//    public List<Developer> getAllBySpecialty(String specialty) {
//        Session session = this.sessionFactory.openSession();
//        Query query = session.createQuery("FROM Developer d where d.specialty= :specialty");
//        query.setParameter("specialty", specialty);
//        List<Developer> result = query.list();
//        session.close();
//        return result;
//    }

    public void addSkillToDev(Long SkillId, Long DevId) throws SQLException {

        Session session = sessionFactory.openSession();
        Transaction transaction2 = session.beginTransaction();
        Developer developerSk = session.get(Developer.class, DevId);
        SkillDAO skillDAO = new HibernateSkillDAOImpl();
        Skill skill = skillDAO.getById(SkillId, new FetchConfig());
        developerSk.getSkills().add(skill);
        session.merge(developerSk);
        transaction2.commit();
        session.close();
    }

    public List<Developer> getDeveloperWithSalaryAbove(BigDecimal salary) {
        Session session = this.sessionFactory.openSession();

        Criteria criteria = session.createCriteria(Developer.class);
        criteria.add(Restrictions.gt("salary", salary));

        List<Developer> result = criteria.list();
        session.close();

        return result;
    }

    public List<Developer> getAllDeveloperSQL() {
        Session session = this.sessionFactory.openSession();

        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM developers");
        sqlQuery.addEntity(Developer.class);
        List<Developer> result = sqlQuery.list();

        session.close();
        return result;
    }
}
