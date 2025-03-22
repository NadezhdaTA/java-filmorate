package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
@Validated
public class FilmController {
    private final FilmService filmService;
    private final UserController userController;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        Film created = filmService.createFilm(film);
        log.debug("Фильм успешно добавлен - {} \n", created);
        return created;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) throws DuplicatedDataException, NotFoundException {
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
    public Collection<String> getPopularFilmList(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

}