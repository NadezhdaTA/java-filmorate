package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class UserService {
    private UserStorage userStorage = new InMemoryUserStorage();

    public User createUser(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    public User getUserById(int id) throws NotFoundException {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с Id = " + id + " не найден."));
    }

    public User updateUser(User newUser) throws DuplicatedDataException, NotFoundException, ValidationException {
        User oldUser = getUserById(newUser.getId());

        userStorage.getUsersList().stream()
                .filter(user -> !user.getEmail().equals(newUser.getEmail()))
                .findAny()
                .orElseThrow(() -> new DuplicatedDataException("Еmail: " + newUser.getEmail() + " уже используется."));


        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setName(newUser.getName());
        oldUser.setBirthday(newUser.getBirthday());

        return userStorage.updateUser(oldUser);
    }

    public Collection<User> getUsersList() {
        return userStorage.getUsersList();
    }


    public void addFriend(int userId, int friendId) throws NotFoundException {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        Set<User> userFriends = userStorage.getFriendsList(userId);
        userFriends.add(friend);
        userStorage.addFriend(userId, userFriends);

        Set<User> friendFriends = userStorage.getFriendsList(friendId);
        friendFriends.add(user);
        userStorage.addFriend(friendId, friendFriends);
    }

    public Collection<String> getFriendsList(int id) {
        return userStorage.getFriendsList(id).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public Collection<String> getCommonFriends(int userId, int friendId) {
        Set<User> friendsList = userStorage.getFriendsList(friendId);
        return userStorage.getFriendsList(userId).stream()
                .filter(friendsList::contains)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public void deleteFriend(int id, int friendId) throws NotFoundException {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        Set<User> userFriends = userStorage.getFriendsList(id);
        userFriends.remove(friend);
        userStorage.addFriend(id, userFriends);

        Set<User> friendFriends = userStorage.getFriendsList(friendId);
        friendFriends.remove(user);
        userStorage.addFriend(friendId, friendFriends);
    }

}
