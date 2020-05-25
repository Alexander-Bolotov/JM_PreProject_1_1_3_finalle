package src.main.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import src.main.model.User;

import java.util.List;

public class UserHibernateDAO implements UserDAO {

    private Session session;

    public UserHibernateDAO(Session session) {
        this.session = session;
    }

    @Override
    public List<User> getAllUser() {
        Transaction transaction = session.beginTransaction();
        try {
            List<User> userList = session.createQuery("FROM User").list();
            transaction.commit();
            return userList;
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserByName(String name) {
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM User where name ='" + name + "'");
            List<User> users = query.list();
            return users.get(0);
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean nameIsExist(String name) {
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM User where name ='" + name + "'");
            List users = query.list();
            return users.size() != 0;
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean idIsExist(long id) {
        return getUserById(id) != null;
    }

    @Override
    public void addUser(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteUserByName(String name) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(getUserByName(name));
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void editeUser(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserById(Long id) {
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM User where id =" + id);
            return (User) session.load(User.class, id);
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }


    }

    public void deleteUserById(long id) {
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE User WHERE id =" + id);
            query.executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}