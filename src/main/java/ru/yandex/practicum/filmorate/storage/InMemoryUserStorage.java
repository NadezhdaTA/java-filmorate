package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private int nextUserId = 1;

    private final Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Set<User>> userFriends = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(nextUserId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public Collection<User> getUsersList() {
        return users.values();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void addFriend(int userId, Set<User> friends) {
        userFriends.put(userId, friends);
    }

    @Override
    public Set<User> getFriendsList(int id) {
        return userFriends.get(id);
    }
}
