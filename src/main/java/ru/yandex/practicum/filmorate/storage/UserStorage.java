package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    Map<Integer, User> users = Map.of();

    User createUser(User user);

    User updateUser(User newUser);

    Collection<User> getUsersList();

    Optional<User> getUserById(int id);

    void addFriend(User user, User friend, Friends userFriend, Friends friendFriend);

    Collection<Friends> getFriendsList(int id);

    void deleteFriend(User user, User friend);
}
