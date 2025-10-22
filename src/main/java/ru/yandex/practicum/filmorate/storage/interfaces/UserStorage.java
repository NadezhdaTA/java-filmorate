package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User newUser);

    Collection<User> getUsersList();

    Optional<User> getUserById(int id) throws SQLException;

    void addFriend(User user, User friend);

    Collection<User> getFriendsList(int id);

    void deleteFriend(User user, User friend);
}
