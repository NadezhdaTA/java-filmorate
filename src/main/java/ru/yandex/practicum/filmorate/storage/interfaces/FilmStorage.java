package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {
    Map<Integer, Film> films = Map.of();

    Film createFilm(Film film);

    Film updateFilm(Film newFilm);

    Collection<Film> getFilms();

    Optional<Film> getFilmById(int id);

    void addLikes(int filmId, int userId);

    void deleteLikes(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);

}
