package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;


@Data
public class User {
    @NonNull
    private String email;
    @NonNull
    private String login;

    private int id;
    private String name;
    private LocalDate birthday;

}
