package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDate;

@Data
public class Film {

    @NotNull
    private String name;

    private String description;
    private Integer id;
    private LocalDate releaseDate;
    Integer duration;

    public Film() {

    }

    public Film(String name) {
        this.name = name;
    }

}
