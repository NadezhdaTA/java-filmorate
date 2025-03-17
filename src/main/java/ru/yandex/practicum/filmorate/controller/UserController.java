package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private int nextUserId = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        try {
            validateUser(user);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw new ValidationException(e.getMessage());
        }

        user.setId(nextUserId);
        nextUserId++;
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.debug("Пользователь успешно добавлен - {} \n", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) throws DuplicatedDataException, NotFoundException, ValidationException {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());

            try {
                validateUser(newUser);
            } catch (ValidationException e) {
                throw new ValidationException(e.getMessage());
            }

            for (User user1 : users.values()) {
                if (newUser.getEmail().equals(user1.getEmail())) {
                    log.error("Этот email уже используется.");
                    throw new DuplicatedDataException();
                }
            }

            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            log.debug("Пользователь успешно обновлен - {} \n", oldUser);
            return oldUser;
        }

        log.error("Пост с id {} не найден", newUser.getId());
        throw new NotFoundException();
    }

    @GetMapping
    public Collection<User> getUsersList() {
        try {
            return users.values();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void validateUser(User user) throws ValidationException {
        if (!user.getEmail().contains("@")) {
            log.error("Email должен содержать знак @.");
            throw new ValidationException();
        } else if (user.getLogin().contains(" ")) {
            log.error("Логин не должен иметь пробелы.");
            throw new ValidationException();
        } else if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем.");
            throw new ValidationException();
        }
    }
}
