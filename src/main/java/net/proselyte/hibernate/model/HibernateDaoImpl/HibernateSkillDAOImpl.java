package net.proselyte.hibernate.model.HibernateDaoImpl;

import net.proselyte.hibernate.Utils.HibConnectionUtil;
import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.POJO.Developer;
import net.proselyte.hibernate.model.POJO.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;


public class HibernateSkillDAOImpl implements SkillDAO {

    private SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();

    public HibernateSkillDAOImpl() {
    }

    public void save(Skill skill){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(skill);
            transaction.commit();
            session.close();
            System.out.println("Skill object was saved");
        }catch(Exception e){
            System.err.println(e);
            return;
        }

    }


    public Skill getById(Long id, FetchConfig fetchConfig) {
        Session session = this.sessionFactory.openSession();
        Skill skill = session.get(Skill.class, id);
        session.close();
        return skill;
    }

    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Skill skill = session.get(Skill.class, id);
        session.delete(skill);
        transaction.commit();
        session.close();
    }

    public List<Skill> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Skill");
        List<Skill> result = query.list();
        session.close();
        return result;
    }

    public void addSkillsToDev(Long skillId, Long devId) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction2 = session.beginTransaction();
        Skill devSkill = session.get(Skill.class, skillId);
        DeveloperDAO developerDAO = new HibernateDeveloperDAOImpl();
        Developer developer = developerDAO.getById(devId, new FetchConfig());
        devSkill.getDevelopers().add(developer);
        session.merge(devSkill);
        transaction2.commit();
        session.close();
    }
}
