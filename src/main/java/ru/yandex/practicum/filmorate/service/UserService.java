package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Validated
public class UserService {
    private final UserStorage userStorage = new InMemoryUserStorage();

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    public User getUserById(int id) throws NotFoundException {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с Id = " + id + " не найден."));
    }

    public User updateUser(User newUser) throws DuplicatedDataException, NotFoundException {
        getUserById(newUser.getId());

        userStorage.getUsersList().stream()
                .filter(user -> !user.getEmail().equals(newUser.getEmail()))
                .findAny()
                .orElseThrow(() -> new DuplicatedDataException("Еmail: " + newUser.getEmail() + " уже используется."));

        return userStorage.updateUser(newUser);
    }

    public Collection<User> getUsersList() {
        return userStorage.getUsersList();
    }


    public void addFriend(int userId, int friendId) throws NotFoundException {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        userStorage.addFriend(user, friend);
    }

    public Collection<String> getFriendsList(int id) {
        return userStorage.getFriendsList(id).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public Collection<String> getCommonFriends(int userId, int friendId) {
        Collection<User> friendsList = userStorage.getFriendsList(friendId);
        return userStorage.getFriendsList(userId).stream()
                .filter(friendsList::contains)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public void deleteFriend(int id, int friendId) throws NotFoundException {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        userStorage.deleteFriend(user, friend);
    }

}
