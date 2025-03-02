package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
public class User {
    @NotNull
    private String email;
    @NotNull
    private String login;

    private Integer id;
    private String name;
    private LocalDate birthday;

    public User() {

    }

    public User(String email, String login) {
        this.email = email;
        this.login = login;
    }

}
