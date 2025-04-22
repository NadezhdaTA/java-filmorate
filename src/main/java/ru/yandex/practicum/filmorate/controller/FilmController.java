package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Validated @RequestBody Film film) {
        Film created = filmService.createFilm(film);
        log.debug("Фильм успешно добавлен - {} \n", created);
        return created;
    }

    @PutMapping
    public Film updateFilm(@Validated @RequestBody Film newFilm)
            throws DuplicatedDataException, NotFoundException, ValidationException {
        Film updated = filmService.updateFilm(newFilm);
        log.debug("Фильм успешно обновлен - {} \n", updated);
        return updated;
    }

    @GetMapping
    public Collection<Film> getFilmsList() {
        return filmService.getFilmsList();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws NotFoundException {
        return filmService.getFilmById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLikes(@PathVariable int id,
                         @PathVariable int userId) {
        filmService.addLikes(id, userId);
        log.debug("Like успешно добавлен.");
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFilmLikes(@PathVariable int id,
                                @PathVariable int userId) {
        filmService.deleteLikes(id, userId);
        log.debug("Like успешно удален.");
    }

    @GetMapping("popular")
    public Collection<Film> getPopularFilmList(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

}