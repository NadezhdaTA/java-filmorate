package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {
    Map<Integer, Film> films = Map.of();

    Film createFilm(Film film) throws ValidationException;

    Film updateFilm(Film newFilm) throws DuplicatedDataException, NotFoundException, ValidationException;

    Collection<Film> getFilmsList();

    Optional<Film> getFilmById(int id);

    void addLikes(int filmId, Set<Integer> userIds);

    Set<Integer> getLikesCount(int filmId);

}
