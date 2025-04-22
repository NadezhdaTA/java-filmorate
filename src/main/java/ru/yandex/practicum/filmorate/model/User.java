package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Slf4j
@EqualsAndHashCode(of = {"id, email"})
public class User {
    private Integer id;
    private String name;

    @NotNull(message = "Email не может быть пустым.")
    @Email(message = "Email должен содержать знак @.")
    private String email;

    @NotBlank(message = "Логин не должен быть пустым или иметь пробелы.")
    private String login;

    @NotNull(message = "Дата рождения не может быть пустой.")
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    public User() {

    }

    public User(String email, String login) {
        this.email = email;
        this.login = login;
    }

}