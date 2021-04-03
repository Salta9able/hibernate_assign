package peaksoft.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception ex) {
            System.out.println("не получилось");
            ex.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            System.out.println("table has been successfully deleted");
            transaction.commit();

        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));

            transaction.commit();
            System.out.println("updated");
        }
        catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("deleted");
        }
        catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        Transaction transaction = null;

        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User").getResultList();
            transaction.commit();
            System.out.println("reteived list of " + list.size() + " size");
        }
        catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User ");
            query.executeUpdate();
            transaction.commit();
            System.out.println("all rows are deleted");
        }
        catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
