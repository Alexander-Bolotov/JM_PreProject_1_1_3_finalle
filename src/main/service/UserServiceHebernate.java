package src.main.service;

import org.hibernate.SessionFactory;
import src.main.dao.UserHibernateDAO;
import src.main.model.User;
import src.main.util.DbHelper;

import java.util.List;


public class UserServiceHebernate {
    private static volatile UserServiceHebernate instance;

    private static SessionFactory sessionFactory;

    private UserServiceHebernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceHebernate getInstance() {
        UserServiceHebernate result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserServiceHebernate.class) {
            if (instance == null) {
                instance = new UserServiceHebernate(DbHelper.getSessionFactory());
            }
            return instance;
        }
    }

    public boolean addUser(User user) {
        if (!new UserHibernateDAO(sessionFactory.openSession()).nameIsExist(user.getName())) {
            new UserHibernateDAO(sessionFactory.openSession()).addUser(user);
            return true;
        }
        return false;
    }

    public void deleteUserByName(String name) {
        if (new UserHibernateDAO(sessionFactory.openSession()).nameIsExist(name)) {
            new UserHibernateDAO(sessionFactory.openSession()).deleteUserByName(name);
        }
    }

    public void editeUser(User user) {
        new UserHibernateDAO(sessionFactory.openSession()).editeUser(user);
    }

    public List<User> getAllUsers() {
        return new UserHibernateDAO(sessionFactory.openSession()).getAllUser();
    }

    public void deleteUserById(long id) {
        new UserHibernateDAO(sessionFactory.openSession()).deleteUserById(id);
    }
}