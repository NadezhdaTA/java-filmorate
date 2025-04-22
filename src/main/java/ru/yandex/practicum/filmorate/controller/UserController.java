package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Validated @RequestBody User user) throws ValidationException, DuplicatedDataException {
        User created = userService.createUser(user);
        log.debug("Пользователь успешно добавлен - {} \n.", created);
        return created;
    }

    @PutMapping
    public User updateUser(@Validated @RequestBody User newUser)
            throws DuplicatedDataException, NotFoundException, ValidationException {
        User updated = userService.updateUser(newUser);
        log.debug("Пользователь успешно обновлен - {} \n.", updated);
        return updated;
    }

    @GetMapping
    public Collection<User> getUsersList() {
        return userService.getUsersList();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) throws NotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUsersFriends(@PathVariable int id) {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Collection<User> getCommonFriends(@PathVariable int id,
                                               @PathVariable int friendId) {
        return userService.getCommonFriends(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id,
                          @PathVariable int friendId) throws NotFoundException {
        userService.addFriend(id, friendId);
        log.debug("Друг успешно добавлен.");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id,
                             @PathVariable int friendId) throws NotFoundException {
        userService.deleteFriend(id, friendId);
        log.debug("Друг успешно удален.");
    }
}
