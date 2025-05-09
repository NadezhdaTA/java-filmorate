package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        log.debug("Пользователь успешно добавлен - {} \n.", created);
        return created;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) throws DuplicatedDataException, NotFoundException {
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
    public Collection<String> getUsersFriends(@PathVariable int id) {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Collection<String> getCommonFriends(@PathVariable int id,
                                               @PathVariable int friendId) {
        return userService.getCommonFriends(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}/status/{status}")
    public void addFriend(@PathVariable int id,
                          @PathVariable int friendId,
                          @PathVariable Friendship status) throws NotFoundException {
        userService.addFriend(id, friendId, status);
        log.debug("Друг успешно добавлен.");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id,
                             @PathVariable int friendId) throws NotFoundException {
        userService.deleteFriend(id, friendId);
        log.debug("Друг успешно удален.");
    }
}
