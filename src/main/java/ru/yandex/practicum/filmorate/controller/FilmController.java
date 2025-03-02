package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int nextFilmId = 1;
    private final LocalDate startDate = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        try  {
            validateFilm(film);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }

        film.setId(nextFilmId++);
        films.put(film.getId(), film);
        log.debug("Фильм успешно добавлен - {} \n", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) throws DuplicatedDataException, NotFoundException, ValidationException {
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());

            try {
                validateFilm(newFilm);
            } catch (ValidationException e) {
                log.error(e.getMessage());
                throw new ValidationException(e.getMessage());
            }

            for (Film film1 : films.values()) {
                if (newFilm.getName().equals(film1.getName())) {
                    log.error("Фильм с таким названием уже существует.");
                    throw new DuplicatedDataException();
                }
            }

            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            log.debug("Фильм успешно обновлен - {} \n", oldFilm);
            return oldFilm;
        }

        log.error("Фильм с id {} не найден", newFilm.getId());
        throw new NotFoundException();
    }

    @GetMapping
    public Collection<Film> getFilmsList() {
        try {
            return films.values();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void validateFilm(Film film) throws ValidationException {
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.error("Описание фильма не может превышать 200 знаков.");
            throw new ValidationException();
        } else if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(startDate)) {
            log.error("Дата релиза не должна быть раньше 28.12.1895г.");
            throw new ValidationException();
        } else if (film.getDuration() != null && film.getDuration().toMinutes() < 0) {
            log.error("Продолжительность фильма должна быть положительным числом.");
            throw new ValidationException();
        } else if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым.");
            throw new ValidationException();
        }
    }
}
