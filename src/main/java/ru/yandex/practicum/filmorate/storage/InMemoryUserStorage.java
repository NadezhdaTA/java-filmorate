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
    private final Map<Integer, Set<User>> userFriends = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(nextUserId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        User oldUser = users.get(newUser.getId());

        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setName(newUser.getName());
        oldUser.setBirthday(newUser.getBirthday());

        users.put(oldUser.getId(), newUser);
        return oldUser;
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
    public void addFriend(User user, User friend) {
        userFriends.computeIfAbsent(user.getId(), u -> new HashSet<>()).add(friend);
        userFriends.computeIfAbsent(friend.getId(), f -> new HashSet<>()).add(user);
    }

    @Override
    public Collection<User> getFriendsList(int id) {
        return userFriends.get(id);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        userFriends.get(user.getId()).remove(friend);
    }
}

