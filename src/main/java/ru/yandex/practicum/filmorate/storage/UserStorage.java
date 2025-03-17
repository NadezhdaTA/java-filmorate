package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    Map<Integer, User> users = Map.of();

    User createUser(User user) throws ValidationException;

    User updateUser(User newUser) throws DuplicatedDataException, NotFoundException, ValidationException;

    Collection<User> getUsersList();

    Optional<User> getUserById(int id);

    void addFriend(int id, Set<User> friends);

    Set<User> getFriendsList(int id);
}
