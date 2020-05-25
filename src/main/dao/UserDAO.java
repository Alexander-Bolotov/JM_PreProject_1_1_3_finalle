package src.main.dao;

import src.main.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUser();

    User getUserByName(String name);

    boolean nameIsExist(String name);

    boolean idIsExist(long id);

    void addUser(User user);

    void deleteUserByName(String name);

    void editeUser(User user);

    User getUserById(Long id);
}
