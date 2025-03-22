package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {
    Map<Integer, Film> films = Map.of();

    Film createFilm(Film film);

    Film updateFilm(Film newFilm);

    Map<Integer, Film> getFilms();

    Optional<Film> getFilmById(int id);

    void addLikes(int filmId, int userId);

    void deleteLikes(int filmId, int userId);

    Map<Integer, Set<Integer>> getPopularFilms(int count);

}
