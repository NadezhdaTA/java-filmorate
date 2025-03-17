package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        Film created = filmService.createFilm(film);
        log.debug("Фильм успешно добавлен - {} \n", created);
        return created;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) throws DuplicatedDataException, NotFoundException, ValidationException {
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
    private void addLikes(@PathVariable int id,
                          @PathVariable int userId) throws NotFoundException {
        filmService.addLikes(id, userId);
        log.debug("Like успешно добавлен.");
    }

    @DeleteMapping("{id}/like/{userId}")
    private void deleteFilmLikes(@PathVariable int id,
                                @PathVariable int userId) throws NotFoundException {
        filmService.deleteLikes(id, userId);
        log.debug("Like успешно удален.");
    }

    @GetMapping("popular?count={count}")
    public Collection<Film> getPopularFilmList(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

}