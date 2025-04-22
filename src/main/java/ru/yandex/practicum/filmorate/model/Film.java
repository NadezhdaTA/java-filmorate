package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@EqualsAndHashCode(of = {"id"})
public class Film {
    private Integer id;

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

    @JsonProperty("genres")
    private Set<Genre> genre = new HashSet<>();

    @JsonProperty("mpa")
    private MpaRating mpaRating;

    public Film() {

    }

    public Film(String name) {
        this.name = name;
    }

}
