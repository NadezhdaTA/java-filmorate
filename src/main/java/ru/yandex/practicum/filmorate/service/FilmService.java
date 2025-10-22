package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;

import java.util.*;

@Service
@Validated
public class FilmService {
    private final FilmDbStorage filmStorage;

    public FilmService (FilmDbStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film getFilmById(int id) throws NotFoundException {
        return  filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с таким id = " + id + " не найден."));
    }

    public Film updateFilm(Film newFilm) throws DuplicatedDataException, NotFoundException {
        getFilmById(newFilm.getId());
        return filmStorage.updateFilm(newFilm);
    }

    public Collection<Film> getFilmsList() {
        return filmStorage.getFilms();
    }

    public void addLikes(int filmId, int userId) {
        filmStorage.addLikes(filmId, userId);
    }

    public void deleteLikes(int filmId, int userId) {
        filmStorage.deleteLikes(filmId, userId);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    public void deleteFilm(int filmId) {
        filmStorage.deleteFilm(filmId);
    }

}
