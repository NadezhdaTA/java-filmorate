package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Past;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Slf4j
public class Film {

    @NotBlank(message = "Название не должно быть пустым или иметь пробелы.")
    private String name;

    @NotNull(message = "Описание фильма не может быть пустым.")
    @Length(max = 200, message = "Описание фильма не может превышать {max} знаков.")
    private String description;

    @Past(message = "Дата выхода фильма не может быть в будущем.")
    @NotNull(message = "Дата выхода фильма не может быть пустой.")
    @FilmStartDate
    private LocalDate releaseDate;

    @NotNull(message = "Продолжительность фильма не может быть пустой.")
    @Positive(message = "Продолжительность фильма не может быть меньше 0.")
    Integer duration;

    private Integer id;
    private Integer likes;

    public Film() {

    }

    public Film(String name) {
        this.name = name;
    }

}
